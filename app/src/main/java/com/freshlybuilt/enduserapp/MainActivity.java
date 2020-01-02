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
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.freshlybuilt.enduserapp.adapters.HomePagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView mBottomNavBar;

    private FragmentManager mManager;

    private ViewPager mViewPager;

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

    }

    private void initUI()
    {
        mBottomNavBar = findViewById(R.id.bottom_nav_bar);
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
}
