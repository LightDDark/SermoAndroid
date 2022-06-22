package com.sermo.sermo_android;

import android.content.Intent;
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

import com.sermo.sermo_android.adapters.ContactAdapter;
import com.sermo.sermo_android.enteties.Contact;
import com.sermo.sermo_android.viewmodels.ContactViewModel;

import java.util.ArrayList;
import java.util.List;


public class ContactsFragment extends Fragment {

    ContactViewModel contactViewModel;
    RecyclerView recycleViewContacts;
    List<Contact> contacts = new ArrayList<>();
    Button addContact,settings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_contacts, container, false);
        // Lookup the recyclerview in activity layout
        recycleViewContacts = (RecyclerView) view.findViewById(R.id.contact_recycler);
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactViewModel.getContacts().observe(getViewLifecycleOwner(), new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> cons) {
                contacts = cons;
                ContactAdapter adapter1 = new ContactAdapter(requireActivity(),contacts);
                // Attach the adapter to the recyclerview to populate items
                recycleViewContacts.setAdapter(adapter1);
                // Set layout manager to position the items
            }
        });
        addContact = (Button) view.findViewById(R.id.btn_addCon);
        addContact.setOnClickListener(v -> {
            Intent contactIntent = new Intent(requireActivity(), NewContactActivity.class);
            startActivity(contactIntent);
        });
        settings = (Button) view.findViewById(R.id.btn_set);
        settings.setOnClickListener(v -> {
            Intent settingsIntent = new Intent(requireActivity(), SettingsActivity.class);
            startActivity(settingsIntent);
        });
        recycleViewContacts.setLayoutManager(new LinearLayoutManager(requireActivity()));
        //recycleViewContacts.setHasFixedSize(true);
        // Listen to notification Broadcast
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(MyApplication.context.getString(R.string.channel_intent));
//        this.registerReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                contactViewModel.reload();
//            }
//        }, filter);
//        // Initialize contacts

        // Create adapter passing in the sample user data
        ContactAdapter adapter = new ContactAdapter(requireActivity(),contacts);
        // Attach the adapter to the recyclerview to populate items
        recycleViewContacts.setAdapter(adapter);
        // Set layout manager to position the items
        // Inflate the layout for this fragment
        return  view;
    }
}