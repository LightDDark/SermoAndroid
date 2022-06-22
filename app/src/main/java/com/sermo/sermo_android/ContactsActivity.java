package com.sermo.sermo_android;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ContactsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_layout);

        //load contacts fragment
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_layout, ContactsFragment.class, null).commit();

    }
}
