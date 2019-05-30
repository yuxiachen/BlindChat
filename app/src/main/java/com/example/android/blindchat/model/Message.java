package com.example.android.blindchat.model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private String text;
    private String username;
    private long messagetime;

    public Message(){

    }
    public Message(String text, String username, String photoUrl){
        this.text = text;
        this.username = username;
        messagetime = new Date().getTime();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getMessagetime() {
        return messagetime;
    }

    public void setMessagetime(long messagetime) {
        this.messagetime = messagetime;
    }
}
