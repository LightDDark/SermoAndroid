package com.sermo.sermo_android.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.sermo.sermo_android.IO.InToken;
import com.sermo.sermo_android.IO.LoginReq;
import com.sermo.sermo_android.MyApplication;
import com.sermo.sermo_android.R;

import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    private CallbackFromLoginApi callback;

    //Interface for callback
    public interface CallbackFromLoginApi {
        void onLoginCompleted(boolean status);
    }

    public LoginAPI(CallbackFromLoginApi callback) {
        Context context = MyApplication.context;
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().retryOnConnectionFailure(true).build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(sharedPref.getString(context.getString(R.string.userServer), ""))
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);

        this.callback = callback;
    }

    public void Login(String userId, String password) {
        Call<InToken> call = webServiceAPI.login(new LoginReq(userId, password));
        call.enqueue(new Callback<InToken>() {
            @Override
            public void onResponse(@NonNull Call<InToken> call, @NonNull Response<InToken> response) {
                boolean b = response.isSuccessful();
                if (b && response.body() != null) {
                    Context context = MyApplication.context;
                    SharedPreferences sharedPref = context.getSharedPreferences(
                            context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    String token = response.body().getToken();
                    editor.putString(context.getString(R.string.userId), userId);
                    editor.putString(context.getString(R.string.token), token);
                    editor.apply();
                    setFirebase();
                }
                callback.onLoginCompleted(b);
            }

            @Override
            public void onFailure(@NonNull Call<InToken> call, @NonNull Throwable t) {
                //callback unsuccessful login
                callback.onLoginCompleted(false);
                Log.d("LoginAPI", t.getMessage());
            }
        });
    }

    private void setFirebase() {
        Context context = MyApplication.context;
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(context.getString(R.string.Firebase), "");
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new OAuthInterceptor()).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(sharedPref.getString(context.getString(R.string.userServer), ""))
                .client(client)
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofit.create(WebServiceAPI.class).setFirebase(token);
    }
}