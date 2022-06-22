package com.sermo.sermo_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sermo.sermo_android.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    Button btn_login;
    //private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());
        setContentView(R.layout.activity_main);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(v-> {
            Intent i = new Intent(this, ContactsActivity.class);
            startActivity(i);
        });
    }

}