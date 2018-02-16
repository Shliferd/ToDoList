package com.example.andrei.mateatodo.remote;

/**
 * Created by Andrei on 1/26/2018.
 */

public class APIUtils {

    private APIUtils() {
    }

    ;

    public static final String API_URL = "http:/172.30.115.195/api.php/";

    public static TaskService getTaskService() {
        return RetrofitClientTask.getClient(API_URL).create(TaskService.class);
    }

    public static UserService getUserService() {
        return RetrofitClientTask.getClient(API_URL).create(UserService.class);
    }
}
