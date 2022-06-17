package com.sermo.sermo_android.IO;

public class OutTransfer extends OutMessage{
    String from;
    String to;

    public OutTransfer(String content, String from, String to) {
        super(content);
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
