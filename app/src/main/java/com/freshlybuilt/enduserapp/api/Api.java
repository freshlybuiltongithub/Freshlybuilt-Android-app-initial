package com.freshlybuilt.enduserapp.api;

import com.freshlybuilt.enduserapp.models.PostsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {


    @GET("get_recent_posts/")
    Call<PostsResponse> getPosts();
}
