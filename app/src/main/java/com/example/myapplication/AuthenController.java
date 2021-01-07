package com.example.myapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthenController {
    public static AuthenService authenticate(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://68.183.234.90:8080/home/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(AuthenService.class);
    }
}
