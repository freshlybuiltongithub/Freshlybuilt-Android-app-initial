package com.freshlybuilt.enduserapp.models;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.freshlybuilt.enduserapp.R;
import com.freshlybuilt.enduserapp.api.Api;
import com.freshlybuilt.enduserapp.api.ApiInstance;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class sign_up extends AppCompatActivity implements View.OnClickListener {
        public TextView customToast;

        private TextInputEditText username,password,confirm_pass,email;
        public String nonce=null;String notify="both";
    ProgressBar mProgressBar;
        private static final Pattern PASSWORD_PATTERN =
                Pattern.compile("^" +
                        "(?=.*[0-9])" +
                        "(?=.*[a-z])" +
                        "(?=.*[A-Z])" +
                        "(?=.*[a-zA-Z])" +
                        "(?=.*[@#$%^&+=])" +
                        "(?=\\S+$)" +
                        ".{8,}" +
                        "$");
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.sign_up);
            username= findViewById(R.id.name);
            email= findViewById(R.id.email_id);
            password=findViewById(R.id.pass);
            confirm_pass=findViewById(R.id.confirm_pass);
            customToast=findViewById(R.id.customtoastText);
            findViewById(R.id.button).setOnClickListener(this);
            mProgressBar = findViewById(R.id.progressBar);
            mProgressBar.setVisibility(View.GONE);


        }

        private void sign_up(){
            mProgressBar.setVisibility(View.VISIBLE);

            String user=username.getText().toString().trim();
            String email_id=  email.getText().toString().trim();
            String Password=password.getText().toString().trim();
            String Confirm_pass=confirm_pass.getText().toString().trim();

            if(user.isEmpty()){
                username.setError("Username is Required");
                username.requestFocus();
                return;
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email_id).matches()){
                email.setError("Email Invalid");
                email.requestFocus();
                return;
            }
            if(Password.isEmpty()){
                password.setError("Password is Required");
                password.requestFocus();
                return;
            }
            if(Password.length()<8){
                password.setError("Password should be 8 characters long!!");
                password.requestFocus();
                return;
            }

            if(!PASSWORD_PATTERN.matcher(Password).matches()){
                password.setError("Password should be Alphanumeric and must contain special symbol!!");
                password.requestFocus();
                return;
            }
            if(Confirm_pass.isEmpty()){
                confirm_pass.setError("You need to Confirm your Password");
                confirm_pass.requestFocus();
                return;
            }
            if(!Confirm_pass.equals(Password)){
                confirm_pass.setError("Confirm password does not match");
                confirm_pass.requestFocus();
                return;
            }


            //API CALL  TO FETCH NONCE VALUE FROM JSON RESPONSE
            Call<ResponseBody> api_CallNonce=ApiInstance.getInstance().getApiNonce().readNonce();
            api_CallNonce.enqueue(new Callback<ResponseBody>()
            {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String Json=null;

                    try{
                        Json=response.body().string();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    if(Json!=null){
                        try{
                            String user=username.getText().toString().trim();
                            String email_id=  email.getText().toString().trim();
                            String Password=password.getText().toString().trim();
                            JSONObject object=new JSONObject(Json);
                            nonce  = object.getString("nonce");
                            //Api Call for REGISTERATION
                            Call<ResponseBody> api_Call= ApiInstance.getInstance().getApi().register(user,email_id,nonce,notify,Password);
                            api_Call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    String s=null;
                                    try{
                                        s=response.body().string();
                                    }
                                    catch(IOException e){
                                        e.printStackTrace();
                                    }
                                    mProgressBar.setVisibility(View.GONE);
                                    if(s!=null){
                                        try {
                                            JSONObject object = new JSONObject(s);
                                            String status = object.getString("status");
                                            if (status.equals("error")) {
                                                String message = object.getString("error");
                                                Log.d("inside",status);
                                                Toast.makeText(sign_up.this, message, Toast.LENGTH_LONG).show();
                                            }
                                            if(status.equals("ok")){
                                                View layout=getLayoutInflater().inflate((R.layout.custom_toast),(ViewGroup)findViewById(R.id.customtoast));
                                                 Toast toast=new Toast(getApplicationContext());
                                                 toast.setGravity(Gravity.CENTER,0,0);
                                                 toast.setView(layout);
                                                 toast.setDuration(Toast.LENGTH_LONG);
                                                 toast.show();
                                            }
                                        }
                                        catch(JSONException e){
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(sign_up.this,"Try Again",Toast.LENGTH_LONG).show();
                                }
                            });
                        }


                        catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                   Toast.makeText(sign_up.this,t.getMessage(),Toast.LENGTH_LONG).show();
                }
            } );
        }
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.button:
                    sign_up();      //METHOD TO VALIDATE AND POST USER DETAILS
                    break;
            }
        }
    }


