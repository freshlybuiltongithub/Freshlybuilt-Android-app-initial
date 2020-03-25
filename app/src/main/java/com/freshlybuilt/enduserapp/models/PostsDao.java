package com.freshlybuilt.enduserapp.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface PostsDao {
    @Insert
    void  insertPost(PostsOffline post);
    @Delete
    void  deletePost(PostsOffline post);
    @Query("Select * from PostsOffline")
    List<PostsOffline> getAllPosts();
}
