package com.sermo.sermo_android.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sermo.sermo_android.IO.OutContact;
import com.sermo.sermo_android.enteties.Contact;
import com.sermo.sermo_android.repositories.ContactRepository;
import com.sermo.sermo_android.repositories.MessageRepository;

import java.io.Serializable;
import java.util.List;

public class ContactViewModel extends ViewModel implements Serializable {
    private transient ContactRepository contactRepository;
    private transient LiveData<List<Contact>> contacts;
    private boolean initialized = false;

    public ContactViewModel() {
        this.contactRepository = new ContactRepository();
        this.contacts = contactRepository.getAll();
    }

    public LiveData<List<Contact>> getContacts() {
        return contacts;
    }

    public void add(String id, String name, String server) {
        OutContact newContact = new OutContact(id,name,server);
        contactRepository.add(newContact);
    }

    public void reload() {
        contactRepository.reload();
    }
}
