package com.sermo.sermo_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.sermo.sermo_android.viewmodels.LoginViewModel;


public class MainActivity extends AppCompatActivity {
    Button btn_login,btn_register;
    EditText username,password;
    LoginViewModel lvm;

    TextView tx1;
    int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        lvm = new ViewModelProvider(this).get(LoginViewModel.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login = (Button)findViewById(R.id.btn_login);
        Intent i = new Intent(this, ContactsActivity.class);



        btn_register = (Button)findViewById(R.id.btn_register);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        //observe login status
        lvm.getStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Toast.makeText(getApplicationContext(),
                            "Successful login!",Toast.LENGTH_SHORT).show();

                    lvm.resetFlags();
                    startActivity(i);
                }
            }
        });
        //observe failed login
        lvm.getFailedLoginStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Toast.makeText(getApplicationContext(),
                            "Failed login! Please check the network and credentials",Toast.LENGTH_LONG).show();

                    lvm.resetFlags();
                }
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvm.login(username.getText().toString(),password.getText().toString());
                Toast.makeText(getApplicationContext(),
                        "Redirecting...",Toast.LENGTH_SHORT).show();
            }
        });
        btn_register.setOnClickListener(v-> {
            Intent in = new Intent(this, RegisterActivity.class);
            startActivity(in);
        });


    }
}