package com.example.android.blindchat.model;

public class Message {
    private String text;
    private String username;
    private String photoUrl;

    public Message(){

    }
    public Message(String text, String username, String photoUrl){
        this.text = text;
        this.username = username;
        this.photoUrl = photoUrl;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
