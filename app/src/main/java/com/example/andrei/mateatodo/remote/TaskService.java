package com.example.andrei.mateatodo.remote;

import com.example.andrei.mateatodo.model.Task;
import com.example.andrei.mateatodo.model.TaskResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Andrei on 1/26/2018.
 */

public interface TaskService {

    @GET("task")
    Call<TaskResponse> getTasks();

    @POST("task")
    Call<Task> addTask(@Body Task task);

    @GET("task")
    Call<TaskResponse> getTasksByUserId(@Query("userId") int userId);

    @PUT("task/{id}")
    @FormUrlEncoded
    Call<Task> getChecked(@Path("id") int id, @Field("checked") int checked);
}
