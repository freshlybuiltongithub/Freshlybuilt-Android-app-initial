package com.freshlybuilt.enduserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.freshlybuilt.enduserapp.adapters.HomePagerAdapter;
import com.freshlybuilt.enduserapp.api.Api;
import com.freshlybuilt.enduserapp.api.ApiInstance;
import com.freshlybuilt.enduserapp.models.PostsResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView mBottomNavBar;

    private FragmentManager mManager;

    private ViewPager mViewPager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        getWindow().setStatusBarColor(Color.BLACK);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer_home);
        actionBar.setDisplayHomeAsUpEnabled(true);

        initUI();

           // mBottomNavBar.setSelectedItemId(R.id.home_nav);

      //  getPosts();

    }

    private void initUI()
    {
        mBottomNavBar = findViewById(R.id.bottom_nav_bar);
       // mBottomNavBar.setSelectedItemId(R.id.home_nav);

        mManager = getSupportFragmentManager();

        mViewPager = findViewById(R.id.fragment_container);


        List<Fragment> fragList = new ArrayList<>();
        fragList.add(new HomeFragment());
        fragList.add(new GroupFragment());
        fragList.add(new HomeFragment());
        fragList.add(new ProfileFragment());

        final HomePagerAdapter adapter = new HomePagerAdapter(mManager,fragList);
        mViewPager.setAdapter(adapter);

        Fragment fragment = mManager.findFragmentById(R.id.fragment_container);
        if(fragment == null)
        {
            fragment = new HomeFragment();
            mManager.beginTransaction()
                    .add(R.id.fragment_container,fragment,"HOME")
                    .commit();
        }

        mBottomNavBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.home_nav:
                        mViewPager.setCurrentItem(0);
                        break;

                    case R.id.groups_nav:
                        mViewPager.setCurrentItem(1);
                        break;

                    case R.id.qna_nav:
                        mViewPager.setCurrentItem(2);
                        break;

                    case R.id.profile_nav:
                        mViewPager.setCurrentItem(3);
                        break;
                }

                return true;
            }
        });
    }

//    private void getPosts()
//    {
//        Retrofit retrofit = ApiInstance.getInstance();
//        Api api  = retrofit.create(Api.class);
//        Call<PostsResponse> call = api.getPosts();
//        call.enqueue(new Callback<PostsResponse>() {
//            @Override
//            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
//                if(response.isSuccessful())
//                {
//                    Log.d("CALL_RESULT",response.body().getStatus());
//
//                }
//                else
//                    Log.d("CALL_RES_ER",response.errorBody().toString());
//            }
//
//            @Override
//            public void onFailure(Call<PostsResponse> call, Throwable t) {
//                    Log.d("CALL_RES_ERROR",t.getMessage());
//            }
//        });
//    }
}
