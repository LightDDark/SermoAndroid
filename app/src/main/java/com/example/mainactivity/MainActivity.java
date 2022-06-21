package com.example.mainactivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity  {
    Button btn_login,btn_register;
    EditText username,password;

    TextView tx1;
    int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login = (Button)findViewById(R.id.btn_login);



        btn_register = (Button)findViewById(R.id.btn_register);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);

        //tx1 = (TextView)findViewById(R.id.textView3);
        // tx1.setVisibility(View.GONE);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("admin") &&
                        password.getText().toString().equals("admin")) { //not real condition, need to put if in database
                    Toast.makeText(getApplicationContext(),
                            "Redirecting...",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();

                    tx1.setVisibility(View.VISIBLE);
                    tx1.setBackgroundColor(Color.RED);
                    counter--;
                    tx1.setText(Integer.toString(counter));

                    if (counter == 0) {
                        Toast.makeText(getApplicationContext(),
                                "Too many tries. Blocked.",Toast.LENGTH_SHORT).show();
                        btn_login.setEnabled(false);
                    }
                }
            }
        });
        btn_register.setOnClickListener(v-> {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        });


    }
}