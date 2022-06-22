package com.sermo.sermo_android.api;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;

import com.sermo.sermo_android.IO.LoginReq;
import com.sermo.sermo_android.IO.RegisterReq;
import com.sermo.sermo_android.MyApplication;
import com.sermo.sermo_android.R;

import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public interface CallbackFromRegisterApi {
        void onRegisterCompleted(boolean status);
    }

    private CallbackFromRegisterApi callback;

    public RegisterAPI(CallbackFromRegisterApi callback) {
        this.callback = callback;
        Context context = MyApplication.context;
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        retrofit = new Retrofit.Builder()
                .baseUrl(sharedPref.getString(context.getString(R.string.userServer), ""))
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void register(String userId, String nick, String password) {
        Call<Void> call = webServiceAPI.register(new RegisterReq(userId, password, nick));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                callback.onRegisterCompleted(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onRegisterCompleted(false);
            }
        });
    }

    public void checkId(String id) {
        Call<LoginReq> call = webServiceAPI.getUser(id);
        call.enqueue(new Callback<LoginReq>() {
            @Override
            public void onResponse(Call<LoginReq> call, Response<LoginReq> response) {
//                idStatus.postValue(!response.isSuccessful());
            }

            @Override
            public void onFailure(Call<LoginReq> call, Throwable t) {
            }
        });
    }
}