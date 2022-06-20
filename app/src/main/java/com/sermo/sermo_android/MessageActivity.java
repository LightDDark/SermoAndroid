package com.sermo.sermo_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.sermo.sermo_android.adapters.MessageAdapter;

public class MessageActivity extends AppCompatActivity {
    private RecyclerView msgRecycler;
    private MessageAdapter msgAdapter;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_chat);

        msgRecycler = findViewById(R.id.main_chat);
        msgRecycler.setLayoutManager(new LinearLayoutManager(this));
        msgAdapter = new MessageAdapter(this, messageList);
        msgRecycler.setAdapter(msgAdapter);
    }
}