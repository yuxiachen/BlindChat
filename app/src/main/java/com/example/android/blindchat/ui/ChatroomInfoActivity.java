package com.example.android.blindchat.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button joinButton;
    private Button exitButton;
    private Chatroom chatroom;
    private String roomKey;
    private String currUid;
    private int member_live;
    private DatabaseReference joinedUserRef;
    private DatabaseReference joinedRoomRef;
    private DatabaseReference roomRef;

    private Query userInJoinedListQuery;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom_info);
        roomKey = getIntent().getStringExtra("roomKey");
        topic = findViewById(R.id.topic_activity_chatroom_info);
        description = findViewById(R.id.tv_description_activity_chatroom_info);
        time = findViewById(R.id.tv_create_time_activity_chatroom_info);
        memberNum = findViewById(R.id.tv_memberNum_activity_chatroom_info);

        joinButton = findViewById(R.id.info_join);
        exitButton = findViewById(R.id.info_leave);

        currUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        actionBar= getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Details");


        roomRef = FirebaseDatabase.getInstance().getReference("Chatrooms").child(roomKey);
        roomRef.addValueEventListener(new ValueEventListener() {
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

        userInJoinedListQuery = FirebaseDatabase.getInstance().getReference("JoinedUsers")
                .child(roomKey).equalTo(currUid);
        userInJoinedListQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    joinButton.setEnabled(false);
                    exitButton.setEnabled(true);
                } else {
                    joinButton.setEnabled(true);
                    exitButton.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void setupView() {
        topic.setText(chatroom.getName());
        description.setText(chatroom.getDescription());
        memberNum.setText(Integer.toString(chatroom.getMember_number()));
        time.setText(chatroom.getCreated_at().substring(0, 10));
    }


    public void join(View view){
        addUserToJoinedList();
        member_live = chatroom.getMember_number();
        addRoomToJoinedList(chatroom);
        roomRef.child("member_number").setValue(member_live);
        Toast.makeText(getApplicationContext(), "Join the room successfully", Toast.LENGTH_LONG).show();
    }

    public void exit(View view){
        removeUserToJoinedList();
        removeRoomToJoinedList();
        member_live = chatroom.getMember_number();
        roomRef.child("member_number").setValue(member_live);
        Toast.makeText(getApplicationContext(), "Leave the room successfully", Toast.LENGTH_LONG).show();
    }


    private void addUserToJoinedList() {
        joinedUserRef = FirebaseDatabase.getInstance().getReference("JoinedUsers").child(roomKey);
        joinedUserRef.child(currUid).setValue(currUid);
    }

    private void addRoomToJoinedList(Chatroom chatroom) {
        joinedRoomRef = FirebaseDatabase.getInstance().getReference("JoinedRooms").child(currUid);
        joinedRoomRef.child(roomKey).setValue(chatroom);
    }


    private void removeUserToJoinedList() {
        joinedUserRef = FirebaseDatabase.getInstance().getReference("JoinedUsers").child(roomKey);
        joinedUserRef.child(currUid).removeValue();
    }

    private void removeRoomToJoinedList() {
        joinedRoomRef = FirebaseDatabase.getInstance().getReference("JoinedRooms").child(currUid);
        joinedRoomRef.child(roomKey).removeValue();
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
}
