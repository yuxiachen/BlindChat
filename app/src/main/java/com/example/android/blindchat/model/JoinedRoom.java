package com.example.android.blindchat.model;

import java.sql.Time;

public class JoinedRoom {

    private Chatroom room;
    private Time joinedAt;

    public JoinedRoom(Chatroom cRoom, Time join){
        room = cRoom;
        joinedAt = join;
    }

    public Chatroom getRoom(){
        return room;
    }

    public String joinedSince(){

        return joinedAt.toString();
    }
}
