package com.sermo.sermo_android.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.sermo.sermo_android.IO.OutContact;
import com.sermo.sermo_android.IO.OutMessage;
import com.sermo.sermo_android.MyApplication;
import com.sermo.sermo_android.api.MessageAPI;
import com.sermo.sermo_android.enteties.Contact;
import com.sermo.sermo_android.enteties.Message;
import com.sermo.sermo_android.rooms.AppDB;
import com.sermo.sermo_android.rooms.MessageDao;

import java.util.LinkedList;
import java.util.List;

public class MessageRepository {
    private MessageDao dao;
    private MessageListData messageListData;
    private MessageAPI api;
    private Contact contact;

    public MessageRepository(String contactId) {
        AppDB db = Room.databaseBuilder(MyApplication.context,
                AppDB.class, "AppDB").build();
        this.dao = db.messageDao();
        this.messageListData = new MessageListData();
        this.api = new MessageAPI(messageListData, dao);
        this.contact = db.contactDao().get(contactId);
    }

    class MessageListData extends MutableLiveData<List<Message>> {

        public MessageListData() {
            super();
            setValue(new LinkedList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(() -> {
                messageListData.postValue(dao.getAll(contact.getId()));
                api.get(contact.getId());
            }).start();
        }
    }

    public LiveData<List<Message>> getAll() {
        return messageListData;
    }

    public void add(OutMessage msg) {
        api.send(msg, new OutContact(contact.getId(), contact.getName(), contact.getServer()));
    }

    public void reload() {
        api.get(contact.getId());
    }
}
