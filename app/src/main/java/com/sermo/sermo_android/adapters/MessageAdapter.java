package com.sermo.sermo_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sermo.sermo_android.R;
import com.sermo.sermo_android.enteties.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    class IncomingMessageViewHolder extends RecyclerView.ViewHolder {
        private TextView content;
        private TextView created;

        IncomingMessageViewHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.content);
            created = (TextView) itemView.findViewById(R.id.created);
        }
    }

    class OutgoingMessageViewHolder extends RecyclerView.ViewHolder {
        private TextView content;
        private TextView created;

        OutgoingMessageViewHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.content);
            created = (TextView) itemView.findViewById(R.id.created);
        }
    }

    private final LayoutInflater messageLayout;
    private ArrayList<Message> messages;

    public MessageAdapter(Context context, List<Message> messageList) {
        messageLayout = LayoutInflater.from(context);
        this.messages = new ArrayList<>();
        this.messages.addAll(messageList);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            //incoming
            View itemView = messageLayout.inflate(R.layout.reciever_chat, parent, false);
            return new IncomingMessageViewHolder(itemView);
        } else {
            //outgoing
            View itemView = messageLayout.inflate(R.layout.sender_chat, parent, false);
            return new OutgoingMessageViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message current = messages.get(position);
        String YYYYMMDD = current.getCreated().substring(0,10);
        String hhmm = current.getCreated().substring(11,15);
        if(current.isSent()) {
            OutgoingMessageViewHolder vh = (OutgoingMessageViewHolder)holder;
            vh.content.setText(current.getContent());
            vh.created.setText(hhmm);
            }
            else{
            IncomingMessageViewHolder vh = (IncomingMessageViewHolder)holder;
            vh.content.setText(current.getContent());
            vh.created.setText(hhmm);
            }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
    @Override
    public int getItemViewType(int position) {
        Message msg = messages.get(position);
        if (msg.isSent()){
            return 2;
        }
        return 1;
    }
}
