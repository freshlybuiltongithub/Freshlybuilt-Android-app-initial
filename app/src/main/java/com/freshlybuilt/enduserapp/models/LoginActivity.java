package com.freshlybuilt.enduserapp.models;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.freshlybuilt.enduserapp.R;
import com.freshlybuilt.enduserapp.api.ApiInstance;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

    public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
        private TextInputEditText login_user, login_pass;
        ProgressBar mProgressBar;
        String login_nonce, valid, cookie;
        SignInButton signIn;
        int RC_SIGN_IN = 0;
        private static final String EMAIL = "email";
        private static final String PROFILE = "public_profile";
        GoogleSignInClient mGoogleSignInClient;
        CallbackManager callbackManager;
        TextView forgot_Pass_btn;
        LoginButton loginButton;
        String username = null;
        static boolean fblogin=false;
        User user;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            signIn = findViewById(R.id.google_login);
            login_user = findViewById(R.id.login_user);
            login_pass = findViewById(R.id.login_pass);
            mProgressBar = findViewById(R.id.progressBar);
            loginButton = (LoginButton) findViewById(R.id.fb_login);


            findViewById(R.id.button_login).setOnClickListener(this);
            findViewById(R.id.signup_tv).setOnClickListener(this);
            findViewById(R.id.forogot).setOnClickListener(this);
            mProgressBar.setVisibility(View.GONE);

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestProfile().requestIdToken(getString(R.string.server_client_id)).build();

            signIn.setSize(SignInButton.SIZE_STANDARD);
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.google_login:
                            signIn();
                            break;

                    }
                }
            });
            callbackManager = CallbackManager.Factory.create();
            loginButton.setPermissions(Arrays.asList(EMAIL, PROFILE));

            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    String accessToken = loginResult.getAccessToken().getToken();
                    Intent in = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(in);
                    finish();
                    loginButton.setVisibility(View.GONE);
                }

                @Override
                public void onCancel() {
                    Toast.makeText(LoginActivity.this, "Login Cancelled", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(FacebookException exception) {
                    Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }

        private void signIn() {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);

        }

        @Override
        protected void onStart() {
            super.onStart();
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            if (account != null) {
             Intent i=new Intent(LoginActivity.this,MainActivity.class);
             startActivity(i);
          finish();
            }
            if(AccessToken.getCurrentAccessToken()!=null){
//                fblogin=true;
                Intent i=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
            if(SharedPrefManager.getInstance(this).isLoggedIn()){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            callbackManager.onActivityResult(requestCode, resultCode, data);
            // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                // The Task returned from this call is always completed, no need to attach
                // a listener.
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }
        }

        private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
            try {
                GoogleSignInAccount account = completedTask.getResult(ApiException.class);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            } catch (ApiException e) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Log.w("Error", "signInResult:failed code=" + e.getStatusCode());

            }
        }


        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.signup_tv:
                    Intent intent = new Intent(this, sign_up.class);
                    startActivity(intent);
                    break;

                case R.id.button_login:
                    login();

                    break;

                case R.id.forogot:
                    forgotPassword(username);
            }
        }


//LOGIN Through FreshlyBuilt


        private void login() {

            username = login_user.getText().toString().trim();
            String password = login_pass.getText().toString().trim();
            if (username.isEmpty()) {
                login_user.setError("Username is Required");
                login_user.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                login_pass.setError("Username is Required");
                login_pass.requestFocus();
                return;
            }
            login_nonce = Login_nonce();
            Log.d("in log", "after nonce");

            ValidateUser(username, password);

            Log.d("in log", "cookie");




        }


        private String Login_nonce() {

            Call<ResponseBody> api_CallNonce = ApiInstance.getInstance().getApiNonce().read_LoginNonce();
            mProgressBar.setVisibility(View.VISIBLE);
            api_CallNonce.enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String Json = null;

                    try {
                        Json = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (Json != null) {
                        try {
                            JSONObject object = new JSONObject(Json);
                            login_nonce = object.getString("nonce");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("FAILURE_nONCE", t.getMessage());
                }
            });
            return login_nonce;
        }

        public void ValidateUser(final String username, String password) {

            Call<ResponseBody> cookie_api = ApiInstance.getInstance().getApiCookie().generate_cookie(login_nonce, username, password);

            cookie_api.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String generate_cookie = null;
                    if (response.body() != null) {
                        try {
                            generate_cookie = response.body().string();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (generate_cookie != null) {

                            try {
                                JSONObject cookie_object = new JSONObject(generate_cookie);
                                cookie = cookie_object.getString("cookie");
                                JSONObject User_session = cookie_object.getJSONObject("user");
                                int id = User_session.getInt("id");
                                String email = User_session.getString("email");
                                user = new User(id, username, email);
                                SharedPrefManager.getInstance(LoginActivity.this).saveUser(user);
                 //API Call for Validation
                                Call<ResponseBody> validate_api = ApiInstance.getInstance().getApiValidate().validate_auth(cookie);
                                validate_api.enqueue(new Callback<ResponseBody>() {

                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        String validation = null;
                                        try {
                                            validation = response.body().string();

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        if (validation != null) {
                                            try {
                                                JSONObject object = new JSONObject(validation);
                                                valid = object.getString("status");

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }


                                            if (valid.equals("ok")) {
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                            if (valid.equals("error")) {
                                                Toast.makeText(LoginActivity.this, "Username or Email may be Incorrect", Toast.LENGTH_LONG).show();
                                                Log.d("Login Failed", "error");
                                            }
                                        }
                                    }


                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(LoginActivity.this, "Usrname or Password may be Incorrect", Toast.LENGTH_LONG).show();
                                        Log.d("Failure in Login", t.getMessage());
                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else{
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this,"Username or Password May be incorrect",Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(LoginActivity.this,"Usrname or Password may be Incorrect",Toast.LENGTH_LONG).show();
                    Log.d("FAILURE_COOKIE", t.getMessage());
                }
            } );
        }


        private void forgotPassword(String username) {
            username = login_user.getText().toString().trim();
            if (username == null ||username.equals("")) {
                login_user.setError("Enter username first");
                login_user.requestFocus();
            }

            Call<ResponseBody> f = ApiInstance.getInstance().getchangePass().forgotPassword(username);
            f.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String string = null;
                    try {
                        string = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (string != null) {
                        try {
                            JSONObject valid_obj = new JSONObject(string);
                            String val = valid_obj.getString("status");
                            String msg = valid_obj.getString("msg");

                            if (val.equals("ok")) {
                                Log.d("Success", "ok");
                                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
                                Log.d("fail", "error");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }}
//    }  SharedPrefManager.getInstance(LoginActivity.this).saveUser(validate.getUser());
//        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish();