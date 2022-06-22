package com.sermo.sermo_android.viewmodels;

import androidx.lifecycle.ViewModel;

import com.sermo.sermo_android.IO.OutContact;
import com.sermo.sermo_android.repositories.ContactRepository;

import java.io.Serializable;

public class NewContactViewModel  extends ViewModel implements Serializable {
    private transient ContactRepository contactRepository;


    public NewContactViewModel() {
        this.contactRepository = new ContactRepository();
    }

    public void add(String id, String name, String server) {
        OutContact newContact = new OutContact(id,name,server);
        contactRepository.add(newContact);
    }

}
