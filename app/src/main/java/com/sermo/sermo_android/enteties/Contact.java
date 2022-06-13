package com.sermo.sermo_android.enteties;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Contact {
    @PrimaryKey
    String id;
    String name;
    String lastMsg;
    Date lastDate;
}
