package com.freshlybuilt.enduserapp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.freshlybuilt.enduserapp.api.ApiInstance;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadingFragment extends DialogFragment {
    TextView quotes,authorName;
    String content,author;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL,R.style.LoadingScreen);

    }



    public static LoadingFragment  getInstance(){return new LoadingFragment();}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.progressbar, container, false);
        quotes=(TextView)view.findViewById(R.id.quote);
        authorName=(TextView) view.findViewById(R.id.author);
        LinearLayout layout=view.findViewById(R.id.LSlayout);
        AnimationDrawable drawable=(AnimationDrawable) layout.getBackground();
        drawable.setEnterFadeDuration(500);
        drawable.setExitFadeDuration(1000);
        drawable.start();

        return view;
    }
    public  void quotesGenerator(){
        Call<ResponseBody> call= ApiInstance.getInstance().getQuotesApi().getQuotes();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String JsonResponse = null;
                    if (response.body() != null) {
                        try {
                            JsonResponse = response.body().string();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (JsonResponse != null) {

                            try {
                                JSONObject object = new JSONObject(JsonResponse);
                                content=object.getString("content");
                                author=object.getString("author");
                                    quotes.setText("<"+content+"/>");
                                authorName.setText("~ "+author);
                            } catch (JSONException e) {
                                Log.d("Exception",e.getMessage());
                            } } } }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
