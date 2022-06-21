package com.sermo.sermo_android.adapters;
import com.sermo.sermo_android.R;
import com.sermo.sermo_android.enteties.Contact;
import com.sermo.sermo_android.enteties.Message;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.constraintlayout.core.motion.utils.Utils;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends RecyclerView.Adapter {
    private class IncomingMessageViewHolder extends RecyclerView.ViewHolder{
        //private final TextView contactId;
        private final TextView content;
        private final TextView created;
        private final ImageView profilePic;

        private IncomingMessageViewHolder(View itemView){
            super(itemView);
            //contactId = itemView.findViewById(R.id.contactId);
            content = itemView.findViewById(R.id.content);
            created = itemView.findViewById(R.id.created);
            profilePic = itemView.findViewById(R.id.contact_profile);
        }
    }
    private class OutgoingMessageViewHolder extends RecyclerView.ViewHolder{
        //private final TextView contactId;
        private final TextView content;
        private final TextView created;

        private OutgoingMessageViewHolder(View itemView){
            super(itemView);
            //contactId = itemView.findViewById(R.id.contactId);
            content = itemView.findViewById(R.id.content);
            created = itemView.findViewById(R.id.created);
        }
    }
    private final LayoutInflater messageLayout;
    private List<Message> messages;
    //1 is for incoming, 0 is outgoing
    private int isIncoming;
    public MessageAdapter(Context context, List<Message> messageList){
        messageLayout = LayoutInflater.from(context);
        this.messages = messageList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 1){
            //incoming
            View itemView = messageLayout.inflate(R.layout.reciever_chat,parent,false);
            return new IncomingMessageViewHolder(itemView);
        }
        else {
            //outgoing
            View itemView = messageLayout.inflate(R.layout.sender_chat, parent, false);
            return new OutgoingMessageViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (messages != null){
            if(holder.getItemViewType() == 0) {
                final Message current = messages.get(position);
                OutgoingMessageViewHolder outHolder = (OutgoingMessageViewHolder)holder;
                //outHolder.contactId.setText(current.getContactId());
                outHolder.content.setText(current.getContent());
                String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(current.getCreated());
                outHolder.created.setText(currentDateTimeString);
            }
            else if(holder.getItemViewType() == 1) {
                final Message current = messages.get(position);
                IncomingMessageViewHolder inHolder = (IncomingMessageViewHolder)holder;
                //inHolder.contactId.setText(current.getContactId());
                inHolder.content.setText(current.getContent());
                String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(current.getCreated());
                inHolder.created.setText(currentDateTimeString);
                inHolder.profilePic.setImageResource(R.drawable.profile);
            }
        }
    }


    @Override
    public int getItemCount() {
        return 0;
    }
    @Override
    public int getItemViewType(int position) {
        Message msg = messages.get(position);
        if (msg.isSent()){
            return 0;
        }
        return 1;
    }
}
