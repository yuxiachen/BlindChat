package com.example.android.blindchat.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.blindchat.R;
import com.example.android.blindchat.model.Chatroom;
import com.example.android.blindchat.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatroomInfoActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private TextView topic;
    private TextView description;
    private TextView time;
    private TextView memberNum;
    private Chatroom chatroom;
    private String roomKey;
    private Query roomQuery;
    private ArrayList<User> joinedUsers;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom_info);
        roomKey = getIntent().getStringExtra("roomKey");
        topic = findViewById(R.id.topic_activity_chatroom_info);
        description = findViewById(R.id.tv_description_activity_chatroom_info);
        time = findViewById(R.id.tv_create_time_activity_chatroom_info);
        memberNum = findViewById(R.id.tv_memberNum_activity_chatroom_info);

        actionBar= getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Details");
        mAuth = FirebaseAuth.getInstance();

        roomQuery = FirebaseDatabase.getInstance().getReference("Chatrooms").child(roomKey);
        roomQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    chatroom = dataSnapshot.getValue(Chatroom.class);
                    setupView();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void setupView() {
        topic.setText(chatroom.getName());
        description.setText(chatroom.getDescription());
        memberNum.setText(Integer.toString(chatroom.getMember_number()));
        time.setText(chatroom.getCreated_at().substring(0, 10));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
/*
    public void join(){
        if (chatroom != null) {
            joinedUsers = chatroom.getJoined_users();
            for (int i = 0; i < joinedUsers.size(); i++) {
                if (joinedUsers.get(i).getEmail() == )
            }
        }
    }

    public void exit(){

    }
    */

}
