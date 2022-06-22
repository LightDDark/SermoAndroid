package com.sermo.sermo_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.sermo.sermo_android.viewmodels.ContactViewModel;
import com.sermo.sermo_android.viewmodels.NewContactViewModel;

public class NewContactActivity extends AppCompatActivity {
    EditText newId,newNickname,newServer;
    Button addNew;
    NewContactViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_new_contact);
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(NewContactViewModel.class);
        this.setTitle("Add New Contact");
        newId = (EditText) findViewById(R.id.new_id);
        newNickname = (EditText) findViewById(R.id.new_nickname);
        newServer = (EditText) findViewById(R.id.new_server);
        addNew = (Button) findViewById(R.id.btn_add_final_contact);
        addNew.setOnClickListener(v -> {
            viewModel.add(newId.getText().toString(),newNickname.getText().toString(),newServer.getText().toString());
            finish();
        });

    }
}