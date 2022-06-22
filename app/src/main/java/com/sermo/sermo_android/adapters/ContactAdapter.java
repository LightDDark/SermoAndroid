package com.sermo.sermo_android.adapters;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.sermo.sermo_android.MessageFragment;
import com.sermo.sermo_android.R;
import com.sermo.sermo_android.enteties.Contact;

import java.util.List;

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
    private FragmentActivity activity;

    public ContactAdapter(FragmentActivity activity, List<Contact> contact){
        contactLayout = LayoutInflater.from(activity);
        contacts = contact;
        this.activity = activity;
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
                //set messages id bundle
                Bundle bundle = new Bundle();
                bundle.putString("id",contact.getId());
                if (v.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    //load messages fragment on main fragment layout
                   activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_layout, MessageFragment.class, bundle).commit();
                } else {
                    //load messages fragment on second fragment layout
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.second_fragment_layout, MessageFragment.class, bundle).commit();
                }

            });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
