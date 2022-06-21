package com.sermo.sermo_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.sermo.sermo_android.adapters.ContactAdapter;
import com.sermo.sermo_android.enteties.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    List<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_layout);

        // Lookup the recyclerview in activity layout
        RecyclerView recycleViewContacts = findViewById(R.id.contact_recycler);

        // Initialize contacts
        contacts.add(new Contact("1","maayan","localhost/1123"));
        // Create adapter passing in the sample user data
        ContactAdapter adapter = new ContactAdapter(this,contacts);
        // Attach the adapter to the recyclerview to populate items
        recycleViewContacts.setAdapter(adapter);
        // Set layout manager to position the items
    }
}
