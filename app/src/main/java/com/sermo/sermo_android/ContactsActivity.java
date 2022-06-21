package com.sermo.sermo_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;


import com.sermo.sermo_android.adapters.ContactAdapter;
import com.sermo.sermo_android.enteties.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    RecyclerView recycleViewContacts;
    ArrayList<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_layout);
        // Lookup the recyclerview in activity layout
        recycleViewContacts = (RecyclerView) findViewById(R.id.contact_recycler);
        recycleViewContacts.setLayoutManager(new LinearLayoutManager(this));
        recycleViewContacts.setHasFixedSize(true);
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
