package com.example.android.blindchat.model;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Chatroom implements Serializable {
    private String name;
    private String description;
    private User creator;
    private float longitude;
    private float latitude;
    private String created_at;
    private int member_number = 1;
    private ArrayList<Message> chat_history = new ArrayList<>();
    private ArrayList<User> joined_users = new ArrayList<>();

    public Chatroom(){
    }

    public Chatroom(String name, String description, String created_at){
        this.name = name;
        this.description = description;
        this.created_at = created_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMember_number() {
        return member_number;
    }

    public void setMember_number(int member_number) {
        this.member_number = member_number;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public ArrayList<User> getJoined_users() {
        return joined_users;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setJoined_users(ArrayList<User> joined_users) {
        this.joined_users = joined_users;
    }

    public ArrayList<Message> getChat_history() {
        return chat_history;
    }

    public void setChat_history(ArrayList<Message> chat_history) {
        this.chat_history = chat_history;
    }

}