package com.example.android.blindchat.model;

import java.sql.Time;
import java.util.ArrayList;

public class ChatRoom {
    private String name;
    private String description;
    private User creator;
    private float longitude;
    private float latitude;
    private Time created_at;
    private ChatroomHistory chatroomHistory;

    private class ChatroomHistory {
        ArrayList<ChatItem> items;
        public class ChatItem {
            private String username;
            private String message;
        }
    }
}
