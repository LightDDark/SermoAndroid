package com.sermo.sermo_android.adapters;
import com.sermo.sermo_android.R;
import com.sermo.sermo_android.enteties.Contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    class ContactViewHolder extends RecyclerView.ViewHolder{
        private TextView contactName;
        Button contactCard;
        LinearLayout contactCardLayout;

        private ContactViewHolder(View itemView){
            super(itemView);
            contactCard = (Button)itemView.findViewById(R.id.message_button);
        }
    }
    private final LayoutInflater contactLayout;
    private List<Contact> contacts;
    public ContactAdapter(Context context, List<Contact> contact){
        contactLayout = LayoutInflater.from(context);
        contacts = contact;
    }
    @NonNull
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = contactLayout.inflate(R.layout.contacts_layout,parent,false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        if (contacts != null){
            Contact contact = contacts.get(position);
            TextView textView = holder.contactName;
            textView.setText(contact.getName());
            Button button = holder.contactCard;
            button.setText(contact.getName());
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
