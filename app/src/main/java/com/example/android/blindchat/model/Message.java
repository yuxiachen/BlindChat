package com.example.android.blindchat.model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private String message, time, date, name;

    public Message(){

    }

    public Message(String message, String time, String date, String name) {
        this.message = message;
        this.time = time;
        this.date = date;
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
