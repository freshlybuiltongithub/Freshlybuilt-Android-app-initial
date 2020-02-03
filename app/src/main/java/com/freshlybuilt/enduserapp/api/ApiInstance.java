package com.freshlybuilt.enduserapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiInstance {

    public static ApiInstance instance;
    public static  String BASE_URL = "https://freshlybuilt.com/api/";
    private static final String REGISTER_URL="https://freshlybuilt.com/api/user/";
    private static final String NONCE_URL="https://freshlybuilt.com/api/";
    private static final String COOKIE_generate_URL="https://freshlybuilt.com/api/user/";
    private static final String validate_URL="https://freshlybuilt.com/api/user/";
    private static final String changePass="https://freshlybuilt.com/api/user/";
    public Retrofit retrofit,Nonce,cookie,validate,base,changepass;
    public static   ApiInstance getInstance()
    {
        if(instance == null)
            instance= new ApiInstance();

        return instance;
    }

    private ApiInstance()
    {
        base = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofit=new Retrofit.Builder().baseUrl(REGISTER_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Nonce=new Retrofit.Builder().baseUrl(NONCE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        cookie=new Retrofit.Builder().baseUrl(COOKIE_generate_URL).addConverterFactory(GsonConverterFactory.create()).build();
        validate=new Retrofit.Builder().baseUrl(validate_URL).addConverterFactory(GsonConverterFactory.create()).build();
        changepass=new Retrofit.Builder().baseUrl(changePass).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public Api getBaseApi(){
        return base.create(Api.class);
    }
    public Api getApi(){
        return retrofit.create(Api.class);
    }
    public Api getApiNonce(){
        return Nonce.create(Api.class);
    }
    public Api getApiCookie(){
        return cookie.create(Api.class);
    }
    public Api getApiValidate(){
        return validate.create(Api.class);
    }
    public Api getchangePass(){return changepass.create(Api.class); }

}
