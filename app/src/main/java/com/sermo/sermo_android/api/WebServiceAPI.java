package com.sermo.sermo_android.api;


import com.sermo.sermo_android.IO.InMessage;
import com.sermo.sermo_android.IO.InToken;
import com.sermo.sermo_android.IO.LoginReq;
import com.sermo.sermo_android.IO.OutContact;
import com.sermo.sermo_android.IO.OutInvite;
import com.sermo.sermo_android.IO.OutMessage;
import com.sermo.sermo_android.IO.OutTransfer;
import com.sermo.sermo_android.IO.RegisterReq;
import com.sermo.sermo_android.enteties.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface WebServiceAPI {
    // Login & Register
    @POST("users/login")
    Call<InToken> login(@Body LoginReq request);

    @POST("users")
    Call<Void> register(@Body RegisterReq request);

    @GET("users/{id}")
    Call<LoginReq> getUser(@Path("id") String id);

    // Firebase
    @POST("users/firebase/{token}")
    Call<Void> setFirebase(@Path("token") String token);

    // Contacts
    @GET("contacts")
    Call<List<Contact>> getContacts(@Header("Authorization") String token);

    @POST("contacts")
    Call<Void> addContact(@Header("Authorization") String token, @Body OutContact contact);

    @GET("contacts/{id}")
    Call<Contact> getContact(@Header("Authorization") String token, @Path("id") String id);

    @PUT("contacts/{id}")
    Call<Void> updateContact(@Header("Authorization") String token, @Path("id") String id, @Body OutContact contact);

    @DELETE("contacts/{id}")
    Call<Void> deleteContact(@Header("Authorization") String token, @Path("id") String id);

    // Messages
    @GET("contacts/{id}/messages")
    Call<List<InMessage>> getMessages(@Header("Authorization") String token, @Path("id") String id);

    @POST("contacts/{id}/messages")
    Call<Void> addMessage(@Header("Authorization") String token, @Path("id") String id, @Body OutMessage msg);

    @GET("contacts/{id}/messages/{id2}")
    Call<InMessage> getMessage(@Header("Authorization") String token, @Path("id") String contactId, @Path("id2") int msgId);

    @PUT("contacts/{id}/messages/{id2}")
    Call<Void> updateMessage(@Header("Authorization") String token, @Path("id") String contactId, @Path("id2") int msgId, @Body OutMessage msg);

    @DELETE("contacts/{id}/messages/{id2}")
    Call<Void> deleteMessage(@Header("Authorization") String token, @Path("id") String contactId, @Path("id2") int msgId);

    // IO
    @POST("invitations")
    Call<Void> invite(@Body OutInvite contact);

    @POST("transfer")
    Call<Void> sendMsg(@Body OutTransfer msg);
}