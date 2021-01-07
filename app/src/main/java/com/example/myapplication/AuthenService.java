package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenService {
    @POST("login")
    Call<BaseResponse<Data>> authenticate(@Body LoginRequest loginRequest);
}
