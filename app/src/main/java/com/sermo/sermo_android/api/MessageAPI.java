package com.sermo.sermo_android.api;

import static androidx.constraintlayout.core.motion.MotionPaths.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.sermo.sermo_android.IO.InMessage;
import com.sermo.sermo_android.IO.OutContact;
import com.sermo.sermo_android.IO.OutMessage;
import com.sermo.sermo_android.IO.OutTransfer;
import com.sermo.sermo_android.MyApplication;
import com.sermo.sermo_android.R;
import com.sermo.sermo_android.enteties.Message;
import com.sermo.sermo_android.rooms.MessageDao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    private MutableLiveData<List<Message>> messageListData;
    private MessageDao dao;

    public MessageAPI(MutableLiveData<List<Message>> messageListData, MessageDao dao) {
        this.messageListData = messageListData;
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

    public void get(String contactId) {
        Context context = MyApplication.context;
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(context.getString(R.string.token), "default");
        Call<List<InMessage>> call = webServiceAPI.getMessages("bearer "+token, contactId);
        call.enqueue(new Callback<List<InMessage>>() {
            @Override
            public void onResponse(@NonNull Call<List<InMessage>> call, @NonNull Response<List<InMessage>> response) {
                if (response.body() != null) {
                    List<Message> messages = new ArrayList<>();
                    for (InMessage msg :
                            response.body()) {
                        messages.add(new Message(0, contactId, msg.getContent(), msg.getCreated(), msg.isSent()));
                    }
                    dao.clear(contactId);
                    dao.insert(messages.toArray(new Message[0]));
                }
                messageListData.postValue(dao.getAll(contactId));

            }

            @Override
            public void onFailure(@NonNull Call<List<InMessage>> call, @NonNull Throwable t) {
                messageListData.postValue(dao.getAll(contactId));
            }
        });
    }

    public void send(OutMessage msg, OutContact contact) {
        Context context = MyApplication.context;
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(context.getString(R.string.token), "default");
        String userId = sharedPref.getString(context.getString(R.string.userId), "");
        OutTransfer transfer = new OutTransfer(msg.getContent(), userId, contact.getId());
        Call<Void> call = webServiceAPI.addMessage("Bearer " + token, contact.getId(), msg);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d(TAG, "onResponse: Message added");
                get(contact.getId());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, "onResponse: Unable to add Message");
                get(contact.getId());
            }
        });
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(contact.getServer())
                    .callbackExecutor(Executors.newSingleThreadExecutor())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Call<Void> voidCall = retrofit.create(WebServiceAPI.class).sendMsg(transfer);
            voidCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.d(TAG, "onResponse: Transfer added");
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.d(TAG, "onResponse: Unable to add Transfer");
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "send: Didn't manage to send Message!");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            new Thread(() -> {
                dao.insert(new Message(0, contact.getId(), msg.getContent(), LocalDateTime.now().toString(), true));
                messageListData.postValue(dao.getAll(contact.getId()));
            }).start();
        }
        //this.get(contact.getId());
    }
}