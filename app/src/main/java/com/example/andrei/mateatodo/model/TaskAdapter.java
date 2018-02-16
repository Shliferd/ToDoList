package com.example.andrei.mateatodo.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrei.mateatodo.R;
import com.example.andrei.mateatodo.remote.APIUtils;
import com.example.andrei.mateatodo.remote.TaskService;

import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Andrei on 1/15/2018.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    List<Task> tasks;
    AssetManager assetManager;
    AppDatabaseRoom appDatabaseRoom;
    TaskService taskService;
    Context myContext;


    public TaskAdapter(List<Task> tasks, Context myContext, AppDatabaseRoom database) {
        this.tasks = tasks;
        this.assetManager = myContext.getAssets();
        appDatabaseRoom = database;
        this.taskService = APIUtils.getTaskService();
        this.myContext = myContext;
    }

    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TaskAdapter.ViewHolder holder, int position) {

        final Task task = tasks.get(position);
        long dateTime = tasks.get(position).getDateTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd yyyy");
        String dateTask = simpleDateFormat.format(dateTime);

        holder.nameTask.setText(tasks.get(position).getName());
        holder.timeTask.setText(dateTask);

        if (task.isChecked()) {
            holder.circleButton.setBackgroundResource(R.drawable.tick);
        } else {
            holder.circleButton.setBackgroundResource(R.drawable.notchecked);
        }

        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/Raleway-Light.ttf");
        holder.nameTask.setTypeface(typeface);
        holder.timeTask.setTypeface(typeface);

        holder.circleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setChecked(!task.isChecked());
                if (task.isChecked()) {
                    holder.circleButton.setBackgroundResource(R.drawable.tick);
                } else {
                    holder.circleButton.setBackgroundResource(R.drawable.notchecked);
                }

                int ch = task.isChecked() ? 1 : 0;
                appDatabaseRoom.taskDao().checkTask(ch, task.getId());
                getTaskCheckedOnline(task.getId());
            }
        });
    }

    private void getTaskCheckedOnline(int id) {
        Call<Task> callChecked = taskService.getChecked(id, 1);
        callChecked.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(myContext, "Good job!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTask;
        TextView timeTask;
        Button circleButton;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTask = itemView.findViewById(R.id.nameTask);
            timeTask = itemView.findViewById(R.id.timeTask);
            circleButton = itemView.findViewById(R.id.circleButton);
        }
    }
}
