package com.freshlybuilt.enduserapp.api;

import com.freshlybuilt.enduserapp.models.Post;
import com.freshlybuilt.enduserapp.models.PostsResponse;
import com.freshlybuilt.enduserapp.models.PostsSearch;
import com.freshlybuilt.enduserapp.models.QuestionList;
import com.freshlybuilt.enduserapp.models.Tag;

import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
@GET("random")
Call<ResponseBody> getQuotes();
    @GET("question/")
    Call<List<QuestionList>> getQues(@Query("page") int page);
    @GET("question/")
    Call<List<QuestionList>> getLatestQues();
@GET("get_search_results/")
Call<PostsSearch> getSearchResults(@Query("search") String Search, @Query("page") int page);
    @GET("question_tag/")
    Call<List<Tag>> getTags(@Query("page") int page);

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
