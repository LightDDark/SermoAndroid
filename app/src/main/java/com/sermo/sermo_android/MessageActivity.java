package com.sermo.sermo_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sermo.sermo_android.adapters.MessageAdapter;
import com.sermo.sermo_android.enteties.Message;
import com.sermo.sermo_android.viewmodels.MessageViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    RecyclerView msgRecycler;
    MessageAdapter msgAdapter;
    ImageView profilePic;
    List<Message> messageList = new ArrayList<>();
    TextView contactName;
    Button sendButton;
    EditText sendBox;
    MessageViewModel viewModel;
    String id;
    int msgcount = 3;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getExtras().get("id").toString();
        setContentView(R.layout.activity_message_chat);
//        viewModel = new ViewModelProvider(this).get(MessageViewModel.class);
//        viewModel.initialize(id);
//        viewModel.getMessages().observe(this, new Observer<List<Message>>() {
//            @Override
//            public void onChanged(List<Message> msgList) {
//                    messageList.addAll(msgList);
//            }
//        });
        sendButton = (Button) findViewById(R.id.send_button);
        sendBox = (EditText) findViewById(R.id.write_message);
        sendButton.setOnClickListener(v -> {
            //viewModel.add(sendBox.getText().toString());
            messageList.add(new Message(msgcount,id,sendBox.getText().toString(),"2022-06-22T05:32:15.063Z",true));
            msgcount++;
        });
        messageList.add(new Message(1, "Alice","Hello","2022-06-22T05:32:15.063Z",true));
        messageList.add(new Message(2, "Me","Hello2","2022-06-22T05:32:15.063Z",false));
        msgRecycler = (RecyclerView) findViewById(R.id.main_chat);
        contactName = (TextView) findViewById(R.id.contact_nickname);
        profilePic = (ImageView) findViewById(R.id.contact_profile);
        contactName.setText(id);
        profilePic.setImageResource(R.drawable.profile);
        msgRecycler.setLayoutManager(new LinearLayoutManager(this));
        msgAdapter = new MessageAdapter(this, messageList);
        msgRecycler.setAdapter(msgAdapter);
    }
}