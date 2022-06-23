package com.sermo.sermo_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sermo.sermo_android.adapters.ContactAdapter;
import com.sermo.sermo_android.enteties.Contact;
import com.sermo.sermo_android.viewmodels.ContactViewModel;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {
    ContactViewModel contactViewModel;
    RecyclerView recycleViewContacts;
    List<Contact> contacts = new ArrayList<>();
    Button addContact, settings;
    EditText newId, newNickname, newServer;
    Button addNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_layout);
        // Lookup the recyclerview in activity layout
        recycleViewContacts = (RecyclerView) findViewById(R.id.contact_recycler);
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactViewModel.getContacts().observe(this, cons -> {
            contacts = cons;
            ContactAdapter adapter1 = new ContactAdapter(ContactsActivity.this, contacts);
            // Attach the adapter to the recyclerview to populate items
            recycleViewContacts.setAdapter(adapter1);
            // Set layout manager to position the items
        });
        addContact = (Button) findViewById(R.id.btn_addCon);
        addContact.setOnClickListener(v -> {
//            Intent contactIntent = new Intent(this, NewContactActivity.class);
//            Bundle b = new Bundle();
//            b.putSerializable("vm", contactViewModel);
//            contactIntent.putExtras(b);
//            startActivity(contactIntent);
            onButtonShowPopupWindowClick(addContact);
        });
        settings = (Button) findViewById(R.id.btn_set);
        settings.setOnClickListener(v -> {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
        });
        recycleViewContacts.setLayoutManager(new LinearLayoutManager(this));
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
        ContactAdapter adapter = new ContactAdapter(this, contacts);
        // Attach the adapter to the recyclerview to populate items
        recycleViewContacts.setAdapter(adapter);
        // Set layout manager to position the items

    }

    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_new_contact, null);

        // create the popup window
        int width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        int height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        newId = (EditText) popupView.findViewById(R.id.new_id);
        newNickname = (EditText) popupView.findViewById(R.id.new_nickname);
        newServer = (EditText) popupView.findViewById(R.id.new_server);
        addNew = (Button) popupView.findViewById(R.id.btn_add_final_contact);
        addNew.setOnClickListener(v -> {
            contactViewModel.add(newId.getText().toString(), newNickname.getText().toString(), newServer.getText().toString());
            popupWindow.dismiss();
            //finish();
        });
        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener((View v, MotionEvent event) -> {
            v.performClick();
            popupWindow.dismiss();
            return true;
        });
    }
}
