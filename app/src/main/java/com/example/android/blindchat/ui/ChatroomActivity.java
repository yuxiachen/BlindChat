package com.example.android.blindchat.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.blindchat.R;
import com.example.android.blindchat.model.Chatroom;
import com.example.android.blindchat.model.Message;
import com.example.android.blindchat.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class ChatroomActivity extends AppCompatActivity {

    private ActionBar mActionBar;
    private EditText mMessageEditText;
    private ScrollView mScrollView;
    private TextView mMessageTextView;
    private ImageButton mSendButton;

    private Chatroom mChatroom;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef, chatRoomRef, chatRoomMessagesRef, chatRoomNameRef;
    private String currentChatName, currentUserID;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chatroom);

        String key = (String)getIntent().getExtras().getSerializable("key");
        Toast.makeText(ChatroomActivity.this, currentChatName, Toast.LENGTH_SHORT).show();

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        chatRoomRef = FirebaseDatabase.getInstance().getReference().child("Chatrooms/" + key);
        chatRoomNameRef = FirebaseDatabase.getInstance().getReference().child("Chatrooms/" + key + "/name");
        chatRoomMessagesRef = chatRoomRef.child("chat_history");
        InitializeFields();

        GetUserInfo();

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveMessageInfoToDatabase(mMessageEditText.getText().toString());

                mMessageEditText.setText("");

                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        chatRoomMessagesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    DisplayMessage(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    DisplayMessage(dataSnapshot);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void InitializeFields() {
        mActionBar = getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mSendButton =  findViewById(R.id.send_message_button);
        mMessageEditText =  findViewById(R.id.input_group_message);
        mMessageTextView =  findViewById(R.id.group_chat_text_display);
        mScrollView =  findViewById(R.id.my_scroll_view);
        chatRoomNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    currentChatName = dataSnapshot.getValue(String.class);
                    mActionBar.setTitle(currentChatName);;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

    private void DisplayMessage(DataSnapshot dataSnapshot){
        Iterator iterator = dataSnapshot.getChildren().iterator();

        while(iterator.hasNext())
        {
            String chatDate = (String) ((DataSnapshot)iterator.next()).getValue();
            String chatMessage = (String) ((DataSnapshot)iterator.next()).getValue();
            String chatName = (String) ((DataSnapshot)iterator.next()).getValue();
            String chatTime = (String) ((DataSnapshot)iterator.next()).getValue();

            mMessageTextView.append(chatName + " :\n" + chatMessage + "\n" + chatTime + "     " + chatDate + "\n\n\n");

            mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
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
