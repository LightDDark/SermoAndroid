package com.sermo.sermo_android.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sermo.sermo_android.IO.OutContact;
import com.sermo.sermo_android.enteties.Contact;
import com.sermo.sermo_android.repositories.ContactRepository;

import java.util.List;

public class ContactViewModel extends ViewModel {
    private ContactRepository contactRepository;
    private LiveData<List<Contact>> contacts;

    public ContactViewModel() {
        this.contactRepository = new ContactRepository();
        this.contacts = contactRepository.getAll();
    }

    public LiveData<List<Contact>> getContacts() {
        return contacts;
    }

    public void add(OutContact contact) {
        contactRepository.add(contact);
    }

    public void reload() {
        contactRepository.reload();
    }
}