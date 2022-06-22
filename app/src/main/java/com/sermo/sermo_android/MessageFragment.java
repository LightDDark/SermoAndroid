package com.sermo.sermo_android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sermo.sermo_android.adapters.MessageAdapter;
import com.sermo.sermo_android.enteties.Message;
import com.sermo.sermo_android.viewmodels.MessageViewModel;

import java.util.ArrayList;
import java.util.List;


public class MessageFragment extends Fragment {
    RecyclerView msgRecycler;
    MessageAdapter msgAdapter;
    ImageView profilePic;
    ArrayList<Message> messageList = new ArrayList<>();
    TextView contactName;
    Button sendButton;
    EditText sendBox;
    MessageViewModel viewModel;
    String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        id = requireArguments().getString("id");

        View view = inflater.inflate(R.layout.fragment_message, container, false);
        // Listen to notification Broadcast
        IntentFilter filter = new IntentFilter();
        filter.addAction(MyApplication.context.getString(R.string.channel_intent));
        requireActivity().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                viewModel.reload();
            }
        }, filter);
        //sendButton = (Button)
        viewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        viewModel.initialize(id);
        viewModel.getMessages().observe(getViewLifecycleOwner(), new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> msgList) {
                messageList.addAll(msgList);
            }
        });
        sendButton = (Button) view.findViewById(R.id.send_button);
        sendBox = (EditText) view.findViewById(R.id.write_message);
        sendButton.setOnClickListener(v -> {
            viewModel.add(sendBox.getText().toString());
        });
        msgRecycler = (RecyclerView) view.findViewById(R.id.main_chat);
        contactName = (TextView) view.findViewById(R.id.contact_nickname);
        profilePic = (ImageView) view.findViewById(R.id.contact_profile);
        contactName.setText(messageList.get(0).getContactId());
        profilePic.setImageResource(R.drawable.profile);
        msgRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        msgAdapter = new MessageAdapter(requireContext(), messageList);
        msgRecycler.setAdapter(msgAdapter);
        // Inflate the layout for this fragment
        return view;
    }
}