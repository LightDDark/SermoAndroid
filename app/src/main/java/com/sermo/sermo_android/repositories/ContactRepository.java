package com.sermo.sermo_android.repositories;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.sermo.sermo_android.IO.OutContact;
import com.sermo.sermo_android.MyApplication;
import com.sermo.sermo_android.api.ContactAPI;
import com.sermo.sermo_android.enteties.Contact;
import com.sermo.sermo_android.rooms.AppDB;
import com.sermo.sermo_android.rooms.ContactDao;

import java.util.LinkedList;
import java.util.List;

public class ContactRepository {
    private ContactDao dao;
    private ContactListData contactListData;
    private ContactAPI api;

    public ContactRepository() {
        AppDB db = Room.databaseBuilder(MyApplication.context,
                AppDB.class, "AppDB").build();
        dao = db.contactDao();
        contactListData = new ContactListData();
        this.api = new ContactAPI(contactListData, dao);
    }

    class ContactListData extends MutableLiveData<List<Contact>> {

        public ContactListData() {
            super();
            setValue(new LinkedList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(() -> {
                contactListData.postValue(dao.index());
                api.get();
            }).start();
        }
    }

    public MutableLiveData<List<Contact>> getAll() {
        return contactListData;
    }

    public void add(OutContact contact) {
        api.add(contact);
    }

    public void reload() {
        api.get();
    }
}
