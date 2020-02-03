package com.freshlybuilt.enduserapp.models;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.freshlybuilt.enduserapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public  class ProfileFragment extends Fragment {
    Uri imageUri;
    RecyclerView mPostsLv;
     CircleImageView profileImg;
     TextView profileName;
     String personName;
     Uri personPhoto;
     String firstname,lastname,id,image_url;
    public Toolbar mAppBar;
    public static final int IMAGECODE=9780;
    public static final int RESULT_OK=-1;
    AccessToken accessToken;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment,container,false);
//        mPostsLv = view.findViewById(R.id.post_lv);
         profileImg=(CircleImageView) view.findViewById(R.id.profile_img);
         profileName=(TextView) view.findViewById(R.id.profile_name);

        fb_userloggedin();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount( getActivity());
        updateUI(acct);
        registerForContextMenu(profileImg);

        return view;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.camera:
                break;
            case R.id.gallery:
                        Intent intent=new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent,IMAGECODE);
                break;
        }
        return true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mPostsLv.setLayoutManager(new LinearLayoutManager(getContext()));
//        mPostsLv.setAdapter(new PostsListAdapter());
    }



    public  void updateUI(GoogleSignInAccount account){
        if (account != null) {
            personName = account.getDisplayName();
            personPhoto = account.getPhotoUrl();
            profileName.setText(personName);
            Glide.with(this).load(String.valueOf(personPhoto)).into(profileImg);
        }
        else if(AccessToken.getCurrentAccessToken()!=null){
           loadUserProfile(AccessToken.getCurrentAccessToken());
        }
        else{}

    }
    URL profilepic;
    public void loadUserProfile(AccessToken accessToken ){

        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {

                     firstname = object.getString("first_name");
                     lastname = object.getString("last_name");
                     id = object.getString("id");
                    Log.d("id",id);
                     image_url = "https://graph.facebook.com/" + id + "/picture?width=60&height=60";
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    profilepic = new URL(image_url);
                }catch(MalformedURLException e){
                    e.printStackTrace();
                }
                profileName.setText(firstname+" "+lastname);
                Glide.with(ProfileFragment.this).load(String.valueOf(profilepic)).into(profileImg);
            }
        });
        Bundle params = new Bundle();
        params.putString("fields", "first_name,last_name,email,id");
        request.setParameters(params);
        request.executeAsync();
//                   Intent intent=new Intent(LoginActivity.this,MainActivity.class);
//                   startActivity(intent);



    }


    private void fb_userloggedin(){
        if(AccessToken.getCurrentAccessToken()!=null){
           loadUserProfile(AccessToken.getCurrentAccessToken());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGECODE&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            imageUri=data.getData();
            Glide.with(this).load(String.valueOf(imageUri)).apply(RequestOptions.circleCropTransform()).into(profileImg);
        }
    }


}
