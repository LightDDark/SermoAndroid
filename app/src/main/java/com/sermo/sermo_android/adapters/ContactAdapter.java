package com.sermo.sermo_android.adapters;
import com.sermo.sermo_android.R;
import com.sermo.sermo_android.enteties.Contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    class ContactViewHolder extends RecyclerView.ViewHolder{
        private final TextView contactName;
        private final TextView lastMsg;
        private final Date lastDate;

        private ContactViewHolder(View itemView){
            super(itemView);
            contactName = itemView.findViewById(R.id.contactName);
            lastMsg = itemView.findViewById(R.id.lastMgs);
            lastDate = itemView.findViewById(R.id.lastDate);
        }
    }
    private final LayoutInflater contactLayout;
    private List<Contact> contacts;
    public ContactAdapter(Context context){
        contactLayout = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = contactLayout.inflate(R.layout.contact_item,parent,false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        if (contacts != null){
            final Contact current = contacts.get(position);
            holder.contactName.setText(current.getName());
            holder.lastMsg.setText(current.getLastMsg());
            long date = holder.lastDate.getTime();
            holder.lastDate.setTime(date);
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
