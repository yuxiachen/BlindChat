package com.example.android.blindchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.android.blindchat.R;
import com.example.android.blindchat.adapter.MessageAdapter;
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

    private DatabaseReference userRef, chatHistoryRef, joinedUserRef;
    private String currentUserID;

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
        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        userRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID);
        chatHistoryRef = FirebaseDatabase.getInstance().getReference("ChatHistory").child(key);
        joinedUserRef = FirebaseDatabase.getInstance().getReference("JoinedUsers").child(key);
        userRef.addValueEventListener(UserListener);
        chatHistoryRef.addValueEventListener(ChatHistoryListener);

        recyclerView = findViewById(R.id.rv_chat_history_chatroom_activity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        chat_history = new ArrayList<>();
        mAdapter = new MessageAdapter(chat_history, userName);
        recyclerView.setAdapter(mAdapter);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveMessageInfoToDatabase(mMessageEditText.getText().toString());

                mMessageEditText.setText("");
            }
        });
    }

    private ValueEventListener ChatHistoryListener = new ValueEventListener() {
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
    };

    private ValueEventListener UserListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                userName = dataSnapshot.getValue(User.class).getUsername();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

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


        Message message = new Message( message_text, currentTime, currentDate, userName);
        chatHistoryRef.push().setValue(message);
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
                this.finish();
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

}
