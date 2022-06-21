package com.sermo.sermo_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.sermo.sermo_android.adapters.MessageAdapter;
import com.sermo.sermo_android.enteties.Message;

import java.util.Date;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    private RecyclerView msgRecycler;
    private MessageAdapter msgAdapter;
    List<Message> messageList;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_chat);

        msgRecycler = findViewById(R.id.main_chat);
        msgRecycler.setLayoutManager(new LinearLayoutManager(this));
        Date d = new Date();
        messageList.add(new Message(1,"1","hello",d,true));
        msgAdapter = new MessageAdapter(this, messageList);
        msgRecycler.setAdapter(msgAdapter);
    }
}