package com.sermo.sermo_android.rooms;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.sermo.sermo_android.enteties.Contact;
import com.sermo.sermo_android.enteties.Message;

@Database(entities = {Contact.class, Message.class}, version = 1)
public abstract class AppDB extends RoomDatabase{
    public abstract ContactDao contactDao();
    public abstract MessageDao messageDao();
}
