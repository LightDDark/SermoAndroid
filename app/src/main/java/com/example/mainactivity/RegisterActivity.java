package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    Button btn_register;
    EditText username,email,password,confirm_password,nickname;
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
        setContentView(R.layout.activity_register);
        btn_register = (Button) findViewById(R.id.btn_register);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        confirm_password = (EditText)findViewById(R.id.confirm_password);
        email = (EditText)findViewById(R.id.email);
        nickname = (EditText)findViewById(R.id.nickname);
        btn_register.setOnClickListener(v->{
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
                Toast.makeText(getApplicationContext(),
                        "Redirecting...",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);

            }
        });

    }
}