package com.example.andrei.mateatodo;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andrei.mateatodo.model.AppDatabaseRoom;
import com.example.andrei.mateatodo.model.Task;
import com.example.andrei.mateatodo.remote.APIUtils;
import com.example.andrei.mateatodo.remote.TaskService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTask extends AppCompatActivity {

    private EditText addNameTastEditText;
    private FloatingActionButton addTaskFloatingActionButton;
    private AppDatabaseRoom databaseRoom;
    TaskService taskService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        databaseRoom = Room.databaseBuilder(getApplicationContext(), AppDatabaseRoom.class, "luciferfall1n1").fallbackToDestructiveMigration().allowMainThreadQueries().build();

        addNameTastEditText = findViewById(R.id.addNameTastEditText);
        addTaskFloatingActionButton = findViewById(R.id.addTaskFloatingActionButton);
        taskService = APIUtils.getTaskService();

        addTaskFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = addNameTastEditText.getText().toString();
                int userId = getIntent().getExtras().getInt("userIdAdd");

                long d = System.currentTimeMillis();

                Task task = new Task(userId, name, d, false);

                boolean connected = false;
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    connected = true;
                } else
                    connected = false;

                if (connected) {
                    databaseRoom.taskDao().insertTask(task);
                    addToServer(task);

                    Intent intent = new Intent(AddTask.this, HomeTODO.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddTask.this, "Connect to the internet please!", Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    private void addToServer(Task task) {

        Call<Task> addCall = taskService.addTask(task);
        addCall.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddTask.this, "Added succesfully!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {

            }
        });
    }
}
