package com.example.android.blindchat.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Chatroom implements Serializable {
    private String id;
    private String name;
    private String description;
    private User creator;
    private float longitude;
    private float latitude;
    private String created_at;
    private String school;
    private int member_number;

    public Chatroom(){
    }

    public Chatroom(String id, String name, String description, String created_at){
        this.id = id;
        this.name = name;
        this.description = description;
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

}