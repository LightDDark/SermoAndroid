package com.sermo.sermo_android.api;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;

import com.sermo.sermo_android.IO.InMessage;
import com.sermo.sermo_android.IO.OutContact;
import com.sermo.sermo_android.IO.OutMessage;
import com.sermo.sermo_android.IO.OutTransfer;
import com.sermo.sermo_android.MyApplication;
import com.sermo.sermo_android.R;
import com.sermo.sermo_android.enteties.Message;
import com.sermo.sermo_android.rooms.MessageDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

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

        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void get(String contactId) {
        Call<List<InMessage>> call = webServiceAPI.getMessages(contactId);
        call.enqueue(new Callback<List<InMessage>>() {
            @Override
            public void onResponse(Call<List<InMessage>> call, Response<List<InMessage>> response) {
                List<Message> messages = new ArrayList<>();
                for (InMessage msg:
                        response.body()) {
                    messages.add(new Message(msg.getId(), contactId, msg.getContent(), msg.getCreated(), msg.isSent()));
                }

                new Thread(() -> {
                    dao.clear(contactId);
                    dao.insert(messages.toArray(new Message[0]));
                    messageListData.postValue(dao.getAll(contactId));
                }).start();
            }

            @Override
            public void onFailure(Call<List<InMessage>> call, Throwable t) {}
        });
    }

    public void send(OutMessage msg, OutContact contact) {
        Context context = MyApplication.context;
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String userId = sharedPref.getString(context.getString(R.string.userId), "");
        OutTransfer transfer = new OutTransfer(msg.getContent(), userId, contact.getId());
        webServiceAPI.addMessage(contact.getId(), msg);
        retrofit = new Retrofit.Builder()
                .baseUrl(contact.getServer())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofit.create(WebServiceAPI.class).sendMsg(transfer);
        this.get(contact.getId());
    }
}