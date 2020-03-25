package com.freshlybuilt.enduserapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.freshlybuilt.enduserapp.models.PostsDao;
import com.freshlybuilt.enduserapp.models.PostsOffline;

@Database(entities = {PostsOffline.class}, version = 1)
public abstract class PostsDatabase extends RoomDatabase {
    public abstract PostsDao postsDao();
    private static PostsDatabase INSTANCE;

    public static PostsDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, PostsDatabase.class, "PostsOffline").allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }
}