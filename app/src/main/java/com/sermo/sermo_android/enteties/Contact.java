package com.sermo.sermo_android.enteties;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Contact {
    @PrimaryKey
            @NonNull
    String id;
    String name;
    String server;
    String lastMsg;
    String lastDate;

    public Contact(@NonNull String id, String name, String server) {
        this.id = id;
        this.name = name;
        this.server = server;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public String getLastDate() {
        return lastDate;
    }

    public String getServer() {
        return server;
    }

    public void setId(@NonNull String id) {
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

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }
}
