package com.freshlybuilt.enduserapp;

import com.freshlybuilt.enduserapp.models.Posts;
import com.freshlybuilt.enduserapp.models.PostsOffline;

public interface ClickListeners {
    void OnPostClick(Posts post);
    void OnbkmbtnClick(PostsOffline posts);
}
