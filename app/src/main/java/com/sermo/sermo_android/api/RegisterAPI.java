package com.sermo.sermo_android.api;

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
    private final MutableLiveData<Boolean> registerStatus;
    private final MutableLiveData<Boolean> idStatus;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public RegisterAPI(MutableLiveData<Boolean> registerStatus, MutableLiveData<Boolean> idStatus) {
        this.registerStatus = registerStatus;
        this.idStatus = idStatus;
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void Register(String userId, String nick, String password) {
        Call<Void> call = webServiceAPI.register(new RegisterReq(userId, password, nick));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                registerStatus.postValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    public void checkId(String id) {
        Call<LoginReq> call = webServiceAPI.getUser(id);
        call.enqueue(new Callback<LoginReq>() {
            @Override
            public void onResponse(Call<LoginReq> call, Response<LoginReq> response) {
                idStatus.postValue(!response.isSuccessful());
            }

            @Override
            public void onFailure(Call<LoginReq> call, Throwable t) {
            }
        });
    }
}