package com.freshlybuilt.enduserapp.api;

import com.freshlybuilt.enduserapp.models.PostsResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {


    @GET("get_recent_posts/")
    Call<PostsResponse> getPosts();

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody>  register(
            @Field("username") String user,
            @Field("email") String email_id,
            @Field("nonce") String nonce,
            @Field("notify") String notify,
            @Field("user_pass") String Password

    );

    @GET("get_nonce/?controller=user&method=register")
    Call<ResponseBody> readNonce();

    @GET("get_nonce/?controller=user&method=generate_auth_cookie")
    Call<ResponseBody> read_LoginNonce();

    @FormUrlEncoded
    @POST("generate_auth_cookie")
    Call<ResponseBody> generate_cookie(
            @Field("nonce") String login_nonce,
            @Field("username") String username,
            @Field("password") String password

    );

    @FormUrlEncoded
    @POST("retrieve_password")
    Call<ResponseBody> forgotPassword(
            @Field("user_login") String username
    );

    @FormUrlEncoded
    @POST("validate_auth_cookie")
    Call<ResponseBody> validate_auth(
            @Field("cookie") String cookie
    );

}
