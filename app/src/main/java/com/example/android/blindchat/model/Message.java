package com.example.android.blindchat.model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private String message, time, date, name;

    //for notification title
    private String roomName, roomKey;
    private Chatroom chatroom;

    public Message(){

    }

    public Message(String message, String time, String date, String name) {
        this.message = message;
        this.time = time;
        this.date = date;
        this.name = name;
    }

    //constructor with room name for notification activity
    public Message(String message, String time, String date, String name, String rName, Chatroom cRoom) {
        this.message = message;
        this.time = time;
        this.date = date;
        this.name = name;
        this.roomName = rName;
        this.chatroom = cRoom;
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

    //for notification
    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String rName) {
        this.roomName = rName;
    }

    public void setRoomKey(String rKey) {this.roomKey = rKey; }

    public String getRoomKey() {return roomKey;}

    public Chatroom getRoom() {
        return chatroom;
    }

    public void setRoom(Chatroom cRoom) {
        this.chatroom = cRoom;
    }


}
