package com.piya.winny.myapplication.module.main.service;

import com.piya.winny.myapplication.module.main.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by piyaponf on 9/22/2017 AD.
 */

public interface MainApiService {
    @GET("/users/{username}")
    Call<User> getUser(@Path("username") String username);
}
