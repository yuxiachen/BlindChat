package com.example.android.blindchat.model;

import java.sql.Time;
import java.util.ArrayList;

public class ChatRoom {
    private String name;
    private String topic;
    private String description;
    private User creator;
    private float longitude;
    private float latitude;
    private Time created_at;
    private ChatroomHistory chatroomHistory;
    private int liveMembers;

    //constructor for layout test
    public ChatRoom(String n, String t, int l){
        name = n;
        topic = t;
        liveMembers = l;
    }

    private class ChatroomHistory {
        ArrayList<ChatItem> items;
        public class ChatItem {
            private String username;
            private String message;
        }
    }

    public String getName(){
        return name;
    }

    public String getTopic(){
        return topic;
    }

    public int getMemberNumber(){
        return liveMembers;
    }

}
