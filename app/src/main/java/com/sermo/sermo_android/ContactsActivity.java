package com.sermo.sermo_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sermo.sermo_android.adapters.ContactAdapter;
import com.sermo.sermo_android.enteties.Contact;
import com.sermo.sermo_android.viewmodels.ContactViewModel;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {
    ContactViewModel contactViewModel;
    RecyclerView recycleViewContacts;
    List<Contact> contacts = new ArrayList<>();
    Button addContact;
    String newId,newNickname,newServer;
    boolean isExtra = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_layout);
        if (getIntent().hasExtra("id")) {
            newId = getIntent().getExtras().getString("id");
            isExtra = true;
        }
        if (getIntent().hasExtra("nickname")) {
            newNickname = getIntent().getExtras().getString("nickname");
        }
        if (getIntent().hasExtra("server")) {
            newServer = getIntent().getExtras().getString("server");
        }
        if (isExtra){
            contacts.add(new Contact(newId,newNickname,newServer));
        }
        // Lookup the recyclerview in activity layout
        recycleViewContacts = (RecyclerView) findViewById(R.id.contact_recycler);
//        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
//        contactViewModel.getContacts().observe(this, new Observer<List<Contact>>() {
//            @Override
//            public void onChanged(List<Contact> cons) {
//                contacts.addAll(cons);
//            }
//        });
        contacts.add(new Contact("1","ExampleBob","localhost:7217"));
        addContact = (Button) findViewById(R.id.btn_addCon);
        addContact.setOnClickListener(v -> {
            Intent clickIntent = new Intent(ContactsActivity.this, NewContactActivity.class);
//            Bundle b = new Bundle();
//            b.putSerializable("vm", contactViewModel);
//            clickIntent.putExtras(b);
            startActivity(clickIntent);
        });
        recycleViewContacts.setLayoutManager(new LinearLayoutManager(this));
        // Create adapter passing in the sample user data
        ContactAdapter adapter = new ContactAdapter(ContactsActivity.this,contacts);
        // Attach the adapter to the recyclerview to populate items
        recycleViewContacts.setAdapter(adapter);
        // Set layout manager to position the items
    }
}
