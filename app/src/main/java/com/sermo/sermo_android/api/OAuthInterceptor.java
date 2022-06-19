package com.sermo.sermo_android.api;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.sermo.sermo_android.MyApplication;
import com.sermo.sermo_android.R;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class OAuthInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Context context = MyApplication.context;
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(context.getString(R.string.token), "default");
        request = request.newBuilder().header("Authorization", "bearer " + token).build();
        return chain.proceed(request);
    }
}
