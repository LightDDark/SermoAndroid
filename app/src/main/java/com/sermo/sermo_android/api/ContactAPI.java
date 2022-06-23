package com.sermo.sermo_android.api;

import static androidx.constraintlayout.core.motion.MotionPaths.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.sermo.sermo_android.IO.OutContact;
import com.sermo.sermo_android.IO.OutInvite;
import com.sermo.sermo_android.MyApplication;
import com.sermo.sermo_android.R;
import com.sermo.sermo_android.enteties.Contact;
import com.sermo.sermo_android.rooms.ContactDao;

import java.util.List;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    private MutableLiveData<List<Contact>> contactListData;
    private ContactDao dao;

    public ContactAPI(MutableLiveData<List<Contact>> contactListData, ContactDao dao) {
        this.contactListData = contactListData;
        this.dao = dao;
        Context context = MyApplication.context;
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new OAuthInterceptor()).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(sharedPref.getString(context.getString(R.string.userServer), ""))
                //.client(client)
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void get() {
        Context context = MyApplication.context;
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(context.getString(R.string.token), "default");
        Call<List<Contact>> call = webServiceAPI.getContacts("bearer "+token);
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(@NonNull Call<List<Contact>> call, @NonNull Response<List<Contact>> response) {

                if (response.body() != null) {
                    dao.clear();
                    dao.insert(response.body().toArray(new Contact[0]));
                }
                contactListData.postValue(dao.index());

            }

            @Override
            public void onFailure(@NonNull Call<List<Contact>> call, @NonNull Throwable t) {
                contactListData.postValue(dao.index());
            }
        });
    }

    public void add(OutContact contact) {
        Context context = MyApplication.context;
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(context.getString(R.string.token), "default");
        String userId = sharedPref.getString(context.getString(R.string.userId), "");
        String userServer = sharedPref.getString(context.getString(R.string.userServer), "");
        OutInvite invitation = new OutInvite(userId, contact.getId(), userServer);
        webServiceAPI.addContact("Bearer " + token, contact);
//        retrofit = new Retrofit.Builder()
//                .baseUrl(contact.getServer())
//                .callbackExecutor(Executors.newSingleThreadExecutor())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        retrofit.create(WebServiceAPI.class).invite(invitation);
        Log.d(TAG, "add: TRY DAO INSERT");
        new Thread(() -> dao.insert(new Contact(contact.getId(), contact.getName(), contact.getServer()))).start();
        Log.d(TAG, "add: MANAGED DAO INSERT");
        this.get();
    }
}