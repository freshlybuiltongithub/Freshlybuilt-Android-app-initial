package com.freshlybuilt.enduserapp;




import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

abstract public class CustomCallback<T> implements Callback<T> {




    @Override
    public void onResponse(Call<T> call, Response<T> response) {

         handleResponse(response, null);


    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

      handleResponse(null, t);

    }




     abstract  void handleResponse(Response<T> response,Throwable t);
}



