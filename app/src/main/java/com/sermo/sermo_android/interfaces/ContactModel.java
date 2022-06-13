package com.sermo.sermo_android.interfaces;

import androidx.lifecycle.LiveData;

import com.sermo.sermo_android.enteties.Contact;

import java.util.List;

public interface ContactModel {
    boolean add(String id, String nick, String server);
    // LiveData<List<Contact>> getAll();
}
