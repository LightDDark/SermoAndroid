package com.sermo.sermo_android;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sermo.sermo_android.viewmodels.RegisterViewModel;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    Button btn_register;
    EditText email,password,confirm_password,nickname;
    RegisterViewModel rvm;
    boolean isMatchingPattern(String email_address, String regexPattern){
        return Pattern.compile(regexPattern).matcher(email_address).matches();
    }
    boolean isValidEmail(String s){
        String emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return isMatchingPattern(s,emailPattern);
    }
    boolean isValidNickname(String s){
        String nicknamePattern = "[a-zA-z0-9]+";
        return isMatchingPattern(s,nicknamePattern);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rvm = new RegisterViewModel();
        setContentView(R.layout.activity_register);
        btn_register = (Button) findViewById(R.id.btn_register);
        password = (EditText)findViewById(R.id.password);
        confirm_password = (EditText)findViewById(R.id.confirm_password);
        email = (EditText)findViewById(R.id.email);
        nickname = (EditText)findViewById(R.id.nickname);

        rvm.getRegisterStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Toast.makeText(getApplicationContext(),
                            "Successful register!",Toast.LENGTH_SHORT).show();

                    rvm.resetFlags();
                    Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
        rvm.getFailedRegisterStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Toast.makeText(getApplicationContext(),
                            "Failed register! Please check the network and that the user is not already exists",Toast.LENGTH_LONG).show();

                }
            }
        });

        btn_register.setOnClickListener(v->{
            handleBtnRegisterClick();

        });

    }

    private void handleBtnRegisterClick() {
        if(!isValidEmail(email.getText().toString())){
            Toast.makeText(getApplicationContext(),
                    "Invalid email format",Toast.LENGTH_SHORT).show();
        }
        else if(password.getText().toString().length()<5){
            Toast.makeText(getApplicationContext(),
                    "Password must be at least 5 characters long",
                    Toast.LENGTH_SHORT).show();

        }
        else if(!password.getText().toString().equals(confirm_password.getText().toString())){
            Toast.makeText(getApplicationContext(),
                    "Passwords must match",Toast.LENGTH_SHORT).show();
        }
        else if(!isValidNickname(nickname.getText().toString())||
                nickname.getText().toString().length()<5){
            Toast.makeText(getApplicationContext(),
                    "nickname must consists of letters and numbers only," +
                            " and be at least 5 characters long",Toast.LENGTH_SHORT).show();
        }
        else{
            rvm.register(email.getText().toString(),
                    password.getText().toString(),nickname.getText().toString());

            Toast.makeText(getApplicationContext(),
                    "Redirecting...",Toast.LENGTH_SHORT).show();
        }
    }
}