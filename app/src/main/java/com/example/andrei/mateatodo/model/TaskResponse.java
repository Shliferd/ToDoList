package com.example.andrei.mateatodo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Andrei on 1/27/2018.
 */

public class TaskResponse {

    @SerializedName("task")
    public List<Task> tasks;
}
