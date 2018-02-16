package com.example.andrei.mateatodo.remote;

import com.example.andrei.mateatodo.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Andrei on 1/27/2018.
 */

public interface UserService {

    @GET("user")
    Call<UserResponse> getUsers();

    @GET("user")
    Call<UserResponse> getUserByUsernameAndPassword(@Query("password") String password, @Query("username") String username);
}
