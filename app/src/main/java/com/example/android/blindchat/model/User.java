package com.example.android.blindchat.model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String email;
    private String username;
    private ArrayList<Chatroom> joinedRoom;

    public User(){}
    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
