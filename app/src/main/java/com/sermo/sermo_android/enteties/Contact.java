package com.sermo.sermo_android.enteties;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Contact {
    @PrimaryKey
    String id;
    String name;
    String server;
    String lastMsg;
    Date lastDate;

    public Contact(String id, String name, String server) {
        this.id = id;
        this.name = name;
        this.server = server;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public String getServer() {
        return server;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }
}
