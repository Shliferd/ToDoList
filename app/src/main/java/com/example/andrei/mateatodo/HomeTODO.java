package com.example.andrei.mateatodo;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrei.mateatodo.model.AppDatabaseRoom;
import com.example.andrei.mateatodo.model.Task;
import com.example.andrei.mateatodo.model.TaskAdapter;
import com.example.andrei.mateatodo.model.TaskResponse;
import com.example.andrei.mateatodo.remote.APIUtils;
import com.example.andrei.mateatodo.remote.TaskService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeTODO extends AppCompatActivity {

    TextView bannerDay;
    TextView bannerDate;
    TextView bannerTime;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    AppDatabaseRoom databaseRoom;
    int userId;
    TaskService taskService;
    List<Task> allTasks = new ArrayList<Task>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_todo);

        bannerDay = findViewById(R.id.bannerDay);
        bannerDate = findViewById(R.id.bannerDate);
        bannerTime = findViewById(R.id.bannerTime);
        recyclerView = findViewById(R.id.recyclerView);
        taskService = APIUtils.getTaskService();

        databaseRoom = Room.databaseBuilder(getApplicationContext(), AppDatabaseRoom.class, "luciferfall1n1").fallbackToDestructiveMigration().allowMainThreadQueries().build();

        long d = System.currentTimeMillis();


        userId = getIntent().getExtras().getInt("userId");
        //List<Task> allTasks = databaseRoom.taskDao().getAllTasks();

        //addTask(userId);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        String dayOfTheWeek = simpleDateFormat.format(d);
        bannerDay.setText(dayOfTheWeek);

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MMM dd yyyy");
        String dateTask = simpleDateFormat1.format(d);
        bannerDate.setText(dateTask);

//        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Raleway-Light.tff");
//        bannerDay.setTypeface(tf);
//        bannerDate.setTypeface(tf);

    }

    private void addTask(int userId) {
        Call<TaskResponse> listCall = taskService.getTasksByUserId(userId);
        listCall.enqueue(new Callback<TaskResponse>() {
            @Override
            public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                if (response.isSuccessful()) {
                    List<Task> tasks = response.body().tasks;
                    gotTheTasks(tasks);
                }
            }

            @Override
            public void onFailure(Call<TaskResponse> call, Throwable t) {
                Log.w("MyTag", "requestFailed", t);
            }
        });
    }

    private void addTaskOffline(int userId) {
        List<Task> tasks = databaseRoom.taskDao().getTasksById(userId);
        Toast.makeText(getApplicationContext(), "S-AU LUAT " + tasks.size() + " taskuri", Toast.LENGTH_SHORT).show();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(tasks, getApplicationContext(), databaseRoom);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void gotTheTasks(List<Task> tasks) {
        List<Task> taskUser = new ArrayList<Task>();
        for (Task task : tasks) {
            if (task.getUserId() == userId) {
                taskUser.add(task);
            }
        }
        Toast.makeText(getApplicationContext(), "S-AU LUAT " + tasks.size() + " taskuri", Toast.LENGTH_SHORT).show();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(taskUser, getApplicationContext(), databaseRoom);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itemAdd) {
            Intent addIntent = new Intent(this, AddTask.class);
            //userId = getIntent().getExtras().getInt("userId");
            addIntent.putExtra("userIdAdd", userId);
            startActivity(addIntent);
        }

        if (id == R.id.itemChart) {
            Intent charActivity = new Intent(HomeTODO.this, ChartActivity.class);
            startActivity(charActivity);
        }

        if (id == R.id.itemMaps) {
            Intent intent = null, chooser = null;
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("geo:46.7562489,23.5628619"));
            chooser = Intent.createChooser(intent, "Open Google Maps");
            startActivity(chooser);
        }

        if (id == R.id.itemAnimation) {
            startActivity(AnimationActivity.getStartIntent(this));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else
            connected = false;

        if (connected) {
            addTask(userId);
        } else {
            addTaskOffline(userId);
        }

        long d = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat1Time = new SimpleDateFormat("h:mm a");
        String timeString = simpleDateFormat1Time.format(d);
        bannerTime.setText(timeString);
//        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Raleway-Light.tff");
//        bannerTime.setTypeface(tf);
    }
}
