package com.freshlybuilt.enduserapp.models;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.freshlybuilt.enduserapp.R;
import com.freshlybuilt.enduserapp.adapters.HomePagerAdapter;
import com.freshlybuilt.enduserapp.api.ApiInstance;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;

    BottomNavigationView mBottomNavBar;

    private FragmentManager mManager;

    private ViewPager mViewPager;

    public static final int IMAGECODE=9780;
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
        getPosts();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestProfile().requestIdToken(getString(R.string.server_client_id)).build();


        mGoogleSignInClient = GoogleSignIn.getClient( this,gso);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.sign_out,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.sign_out_option:
                signout();
        }
        return true;
    }


    public void signout(){

        if(AccessToken.getCurrentAccessToken()!=null){
            LoginManager.getInstance().logOut();
            Intent intent=new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        mGoogleSignInClient.revokeAccess().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } }});
        if(SharedPrefManager.getInstance(this).isLoggedIn()) {
            SharedPrefManager.getInstance(MainActivity.this).clear();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.updateprofilephoto,menu);
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

    @Override
    protected void onStart() {
        super.onStart();
        if(AccessToken.getCurrentAccessToken()!=null){
            LoginActivity.fblogin=true;
        }
    }

    private void getPosts()
    {
        Call<PostsResponse> call =   ApiInstance.getInstance().getBaseApi().getPosts();
        call.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                if(response.isSuccessful())
                {
                    Log.d("CALL_RESULT",response.body().getStatus());

                }
                else
                    Log.d("CALL_RES_ER",response.errorBody().toString());
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                    Log.d("CALL_RES_ERROR",t.getMessage());
            }
        });
    }


}
