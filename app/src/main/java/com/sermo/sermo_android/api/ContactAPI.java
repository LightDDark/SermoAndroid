package com.sermo.sermo_android.api;

import android.content.Context;
import android.content.SharedPreferences;

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
                 .client(client)
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         webServiceAPI = retrofit.create(WebServiceAPI.class);
     }

     public void get() {
         Call<List<Contact>> call = webServiceAPI.getContacts();
         call.enqueue(new Callback<List<Contact>>() {
         @Override
         public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {

             new Thread(() -> {
                 dao.clear();
                 dao.insert(response.body().toArray(new Contact[0]));
                 contactListData.postValue(dao.index());
                 }).start();
         }

                 @Override
         public void onFailure(Call<List<Contact>> call, Throwable t) {}
         });
     }

     public void add(OutContact contact) {
         Context context = MyApplication.context;
         SharedPreferences sharedPref = context.getSharedPreferences(
                 context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
         String userId = sharedPref.getString(context.getString(R.string.userId), "");
         String userServer = sharedPref.getString(context.getString(R.string.userServer), "");
         OutInvite invitation = new OutInvite(userId, contact.getId(), userServer);
         webServiceAPI.addContact(contact);
         retrofit = new Retrofit.Builder()
                 .baseUrl(contact.getServer())
                 .callbackExecutor(Executors.newSingleThreadExecutor())
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
         retrofit.create(WebServiceAPI.class).invite(invitation);
         this.get();
     }
}