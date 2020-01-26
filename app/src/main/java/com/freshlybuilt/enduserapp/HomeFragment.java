package com.freshlybuilt.enduserapp;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freshlybuilt.enduserapp.adapters.PostsListAdapter;
import com.freshlybuilt.enduserapp.api.Api;
import com.freshlybuilt.enduserapp.api.ApiInstance;
import com.freshlybuilt.enduserapp.models.Posts;
import com.freshlybuilt.enduserapp.models.PostsResponse;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {


    RecyclerView mPostsListView;
    private Callback Callback;
    ArrayList<Posts> PostsResponsesList = new ArrayList<>();
    PostsListAdapter postsListAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);
        mPostsListView = view.findViewById(R.id.posts_listview);
         postsListAdapter = new PostsListAdapter(PostsResponsesList, getContext(), new ClickListeners() {

             @Override
             public void OnPostClick(Posts post) {
                 Log.d("TAG2","post is clicked");
                 String url = post.getUrl();
                 CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                 CustomTabsIntent customTabsIntent = builder.build();
                 customTabsIntent.launchUrl(Objects.requireNonNull(getContext()), Uri.parse(url));
             }
         });

        LinearLayoutManager llm= new LinearLayoutManager(getContext());
        mPostsListView.setLayoutManager(llm);
       mPostsListView.setAdapter(postsListAdapter);
       loadPosts();





        return view;
    }
    void loadPosts() {
        Retrofit Client = ApiInstance.getInstance();
        Client.create(Api.class).getPostsResponse().enqueue(new CustomCallback<PostsResponse>() {
            @Override
            void handleResponse(Response<PostsResponse> response, Throwable t) {
                if (response.isSuccessful()) {
                    PostsResponsesList.addAll(Objects.requireNonNull(response.body()).getPosts());
                }
                postsListAdapter.notifyDataSetChanged();
            }


            // Log.v("TAG","size"+response.body().size());


        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mPostsListView.setLayoutManager(new LinearLayoutManager(getContext()));
////        mPostsListView.setLayoutManager(new GridLayoutManager(getContext(),2));
      //  mPostsListView.setAdapter(new PostsListAdapter());
    }
}
