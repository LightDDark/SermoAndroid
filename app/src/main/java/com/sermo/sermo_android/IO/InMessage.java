package com.sermo.sermo_android.IO;

import java.util.Date;

public class InMessage {
    int id;
    String content;
    Date created;
    boolean sent;

    public InMessage(int id, String content, Date created, boolean sent) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.sent = sent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }
}
