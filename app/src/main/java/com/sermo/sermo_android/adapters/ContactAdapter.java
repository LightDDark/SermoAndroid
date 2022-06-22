package com.sermo.sermo_android.adapters;
import com.sermo.sermo_android.MessageActivity;
import com.sermo.sermo_android.R;
import com.sermo.sermo_android.enteties.Contact;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    class ContactViewHolder extends RecyclerView.ViewHolder{
        private TextView contactName;
        Button contactCard;
        RelativeLayout relativeLayout;

        private ContactViewHolder(View itemView){
            super(itemView);
            contactCard = (Button)itemView.findViewById(R.id.contact_button);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.contact_recycler);
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
        View itemView = contactLayout.inflate(R.layout.contact_card_layout,parent,false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
            Contact contact = contacts.get(position);
            //holder.contactName.setText(contact.getName());
            holder.contactCard.setText(contact.getName());
            holder.contactCard.setOnClickListener(v -> {
                Intent clickIntent = new Intent(v.getContext(), MessageActivity.class);
                clickIntent.putExtra("id",contact.getId());
                v.getContext().startActivity(clickIntent);
            });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
