package com.sermo.sermo_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.EditText;

import com.sermo.sermo_android.enteties.Contact;
import com.sermo.sermo_android.viewmodels.ContactViewModel;

import java.util.ArrayList;
import java.util.List;

public class NewContactActivity extends AppCompatActivity {
    EditText newId,newNickname,newServer;
    Button addNew;
    ContactViewModel viewModel;
    List<Contact> contactList = new ArrayList<Contact>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent i = getIntent();
//        Bundle bundle = i.getExtras();
//        viewModel = (ContactViewModel) bundle.getSerializable("vm");
        setContentView(R.layout.activity_new_contact);
        this.setTitle("Add New Contact");
        newId = (EditText) findViewById(R.id.new_id);
        newNickname = (EditText) findViewById(R.id.new_nickname);
        newServer = (EditText) findViewById(R.id.new_server);
        addNew = (Button) findViewById(R.id.btn_add_final_contact);
        addNew.setOnClickListener(v -> {
            //viewModel.add(newId.getText().toString(),newNickname.getText().toString(),newServer.getText().toString());
            Intent clickIntent = new Intent(this, ContactsActivity.class);
            clickIntent.putExtra("id", newId.getText().toString());
            clickIntent.putExtra("nickname", newNickname.getText().toString());
            clickIntent.putExtra("server", newServer.getText().toString());
            startActivity(clickIntent);
        });

    }
}