package com.sermo.sermo_android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sermo.sermo_android.adapters.ContactAdapter;
import com.sermo.sermo_android.enteties.Contact;
import com.sermo.sermo_android.viewmodels.ContactViewModel;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {
    ContactViewModel contactViewModel;
    RecyclerView recycleViewContacts;
    ArrayList<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_layout);
        // Lookup the recyclerview in activity layout
        recycleViewContacts = (RecyclerView) findViewById(R.id.contact_recycler);
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactViewModel.getContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> cons) {
                contacts.addAll(cons);
            }
        });
        recycleViewContacts.setLayoutManager(new LinearLayoutManager(this));
        recycleViewContacts.setHasFixedSize(true);
        // Listen to notification Broadcast
        IntentFilter filter = new IntentFilter();
        filter.addAction(MyApplication.context.getString(R.string.channel_intent));
        this.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                contactViewModel.reload();
            }
        }, filter);
        // Initialize contacts
        contacts.add(new Contact("1","maayan","localhost/1123"));
        contacts.add(new Contact("2","maayan1","localhost/1123"));
        contacts.add(new Contact("3","maayan2","localhost/1123"));
        contacts.add(new Contact("4","maayan3","localhost/1123"));

        // Create adapter passing in the sample user data
        ContactAdapter adapter = new ContactAdapter(this,contacts);
        // Attach the adapter to the recyclerview to populate items
        recycleViewContacts.setAdapter(adapter);
        // Set layout manager to position the items
    }
}
