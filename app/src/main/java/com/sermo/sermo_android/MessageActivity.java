package com.sermo.sermo_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.sermo.sermo_android.adapters.MessageAdapter;
import com.sermo.sermo_android.enteties.Message;
import com.sermo.sermo_android.viewmodels.MessageViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    RecyclerView msgRecycler;
    MessageAdapter msgAdapter;
    ArrayList<Message> messageList = new ArrayList<>();
    //MessageViewModel viewModel;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_chat);
//        viewModel = new ViewModelProvider(this).get(MessageViewModel.class);
//        viewModel.getMessages().observe(this, new Observer<List<Message>>() {
//            @Override
//            public void onChanged(List<Message> msgList) {
//                    messageList.addAll(msgList);
//            }
//        });
        messageList.add(new Message(1, "Me","Hello","today",true));
        messageList.add(new Message(2, "Me","Hello2","today",true));
        msgRecycler = (RecyclerView) findViewById(R.id.main_chat);
        //msgRecycler.setHasFixedSize(true);
        msgRecycler.setLayoutManager(new LinearLayoutManager(this));
        msgAdapter = new MessageAdapter(this, messageList);
        msgRecycler.setAdapter(msgAdapter);
    }
}