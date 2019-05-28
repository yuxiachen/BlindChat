package com.example.android.blindchat.model;

import java.sql.Time;

public class JoinedRoom {

    private ChatRoom room;
    private Time joinedAt;

    public JoinedRoom(ChatRoom cRoom, Time join){
        room = cRoom;
        joinedAt = join;
    }

    public ChatRoom getRoom(){
        return room;
    }

    public String joinedSince(){

        return joinedAt.toString();
    }
}
