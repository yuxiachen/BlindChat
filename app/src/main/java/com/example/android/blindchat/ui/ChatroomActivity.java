package com.example.android.blindchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.android.blindchat.R;
import com.example.android.blindchat.adapter.MessageAdapter;
import com.example.android.blindchat.model.Chatroom;
import com.example.android.blindchat.model.Message;
import com.example.android.blindchat.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ChatroomActivity extends AppCompatActivity {

    private ActionBar mActionBar;
    private EditText mMessageEditText;
    private ImageButton mSendButton;

    private Chatroom mChatroom;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef, chatRoomRef, chatRoomMessagesRef;
    private String currentUserID;
    private User currentUser;

    private ArrayList<Message> chat_history;
    private RecyclerView recyclerView;
    private MessageAdapter mAdapter;
    private String key;
    private String roomName;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        key = getIntent().getStringExtra("key");
        roomName = getIntent().getStringExtra("roomName");


        mActionBar = getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mSendButton =  findViewById(R.id.send_message_button);
        mMessageEditText =  findViewById(R.id.input_group_message);
        mActionBar.setTitle(roomName);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        chatRoomRef = FirebaseDatabase.getInstance().getReference().child("Chatrooms/" + key);
        chatRoomMessagesRef = chatRoomRef.child("chat_history");
        GetUserInfo();

        recyclerView = findViewById(R.id.rv_chat_history_chatroom_activity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        chat_history = new ArrayList<>();
        mAdapter = new MessageAdapter(chat_history, userName);
        recyclerView.setAdapter(mAdapter);

        InitializeFields();

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveMessageInfoToDatabase(mMessageEditText.getText().toString());

                mMessageEditText.setText("");
            }
        });


    }

    @Override
    protected void onStart(){
        super.onStart();
        chatRoomMessagesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    chat_history.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Message currMessage = snapshot.getValue(Message.class);
                        chat_history.add(currMessage);
                    }
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void InitializeFields() {


        chatRoomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    mChatroom = dataSnapshot.getValue(Chatroom.class);
                    TryToAddUserToRoom();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void TryToAddUserToRoom() {
        if (mChatroom !=null && currentUser != null) {
            boolean joined = false;
            for (User user : mChatroom.getJoined_users()) {
                if (user.getEmail().equals(currentUser.getEmail())) {
                    joined = true;
                }
            }
            if (!joined) {
                ArrayList<User> users = mChatroom.getJoined_users();
                users.add(currentUser);
                mChatroom.setJoined_users(users);
                chatRoomRef.setValue(mChatroom);
            }
        }
    }

    private void GetUserInfo(){
        usersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    currentUser = dataSnapshot.getValue(User.class);
                    userName = currentUser.getUsername();
                    TryToAddUserToRoom();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void SaveMessageInfoToDatabase(String message_text){
        if (TextUtils.isEmpty(message_text)) {
            Toast.makeText(this, "Please write message first...", Toast.LENGTH_SHORT).show();
            return;
        }
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        String currentDate = currentDateFormat.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
        String currentTime = currentTimeFormat.format(calForTime.getTime());


        Message message = new Message( message_text, currentTime, currentDate, currentUser.getUsername());
        if (mChatroom != null) {
            mChatroom.getChat_history().add(message);
            chatRoomRef.setValue(mChatroom);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chatroom_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_info:
                openInfoActivity(key);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openInfoActivity(String roomKey){
        Intent intent = new Intent(this, ChatroomInfoActivity.class);
        intent.putExtra("roomKey", roomKey);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return;
    }


}
