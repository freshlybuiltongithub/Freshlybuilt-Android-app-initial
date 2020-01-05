package com.freshlybuilt.enduserapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiInstance {

    public static Retrofit instance;
    public static  String BASE_URL = "https://freshlybuilt.com/api/";


    public static Retrofit getInstance()
    {
        if(instance == null)
             new ApiInstance();

        return instance;
    }

    private ApiInstance()
    {
        instance = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }



}
