package com.freshlybuilt.enduserapp;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.freshlybuilt.enduserapp.adapters.PostsListAdapter;
import com.freshlybuilt.enduserapp.api.Api;
import com.freshlybuilt.enduserapp.api.ApiInstance;
import com.freshlybuilt.enduserapp.models.Posts;
import com.freshlybuilt.enduserapp.models.PostsOffline;
import com.freshlybuilt.enduserapp.models.PostsResponse;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {


    RecyclerView mPostsListView;
    private Callback Callback;
    ArrayList<Posts> PostsResponsesList = new ArrayList<>();
    PostsListAdapter postsListAdapter;
    ProgressBar mprogressBar;
    boolean isScrolling;
    int startPage=0;
    int totalPages;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);
        mPostsListView = view.findViewById(R.id.posts_listview);
//        mprogressBar = (ProgressBar)view.findViewById(R.id.probar);
       //  ImageView bkm_image=view.findViewById(R.id.bookmark_post);

          final PostsDatabase db=PostsDatabase.getInstance(getContext());

         postsListAdapter = new PostsListAdapter(PostsResponsesList, getContext(), new ClickListeners() {

             @Override
             public void OnPostClick(Posts post) {

                 String url = post.getUrl();
                 CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

                 CustomTabsIntent customTabsIntent = builder.build();
                 customTabsIntent.launchUrl(Objects.requireNonNull(getContext()), Uri.parse(url));
             }

             @Override
             public void OnbkmbtnClick(PostsOffline posts) {
               db.postsDao().insertPost(posts);
                 Log.d("bkm","Post is bookmarked");
             }
         });

        final LinearLayoutManager llm= new LinearLayoutManager(getContext());
        mPostsListView.setLayoutManager(llm);
       mPostsListView.setAdapter(postsListAdapter);
        mPostsListView.setItemAnimator(new DefaultItemAnimator());
        loadPosts(startPage);
        mPostsListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                    Log.d("TAG", "scrolling is $isScrolling");
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int currentItems=llm.getChildCount();
                int  totalItems=llm.getItemCount();
               int  scrollOutItems =llm.findFirstVisibleItemPosition();
                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    startPage=getRandomNum(totalPages);
                   Log.i("scroll", "page value is "+startPage);
//                    root.LoadingCircle.visibility=View.VISIBLE
                     loadPosts(startPage);
                   // loaddatafromAPI(startpage,filter)
//                    root.LoadingCircle.visibility=View.INVISIBLE

//
                }
            }
        });


       return view;
    }
    void loadPosts(int startPage) {
        Retrofit Client = ApiInstance.getInstance();
        Client.create(Api.class). getPostsResponse(startPage).enqueue(new CustomCallback<PostsResponse>() {
            @Override
            void handleResponse(Response<PostsResponse> response, Throwable t) {
                if (response!=null) {
                    PostsResponsesList.addAll(Objects.requireNonNull(response.body()).getPosts());
                    totalPages =  response.body().getPages();
                  //  Log.i("scroll", "page value is "+totalPages);

                    postsListAdapter.notifyDataSetChanged();


                }
                else {
                    Toast.makeText(getContext(), "Check Your Internet Connection",Toast.LENGTH_LONG).show();
                }

            }


            // Log.v("TAG","size"+response.body().size());


        });
    }
    int getRandomNum(int totalPages){
        Random random = new Random();
        int randomNumber = random.nextInt(totalPages);
        return randomNumber;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mPostsListView.setLayoutManager(new LinearLayoutManager(getContext()));
////        mPostsListView.setLayoutManager(new GridLayoutManager(getContext(),2));
      //  mPostsListView.setAdapter(new PostsListAdapter());
    }
}
