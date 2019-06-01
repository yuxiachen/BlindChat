package com.example.android.blindchat.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.blindchat.R;
import com.example.android.blindchat.adapter.MessageAdapter;
import com.example.android.blindchat.model.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChatroomActivity extends AppCompatActivity {

    private String TAG = "ChatroomActivity";

    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;

    private Toolbar mToolbar;
    //private MessageAdapter mMessageAdapter;
    private EditText mMessageEditText;
    private ScrollView mScrollView;
    private TextView mMessageTextView;
    private Button mSendButton;

    private String mUsername;
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef, GroupMessageRef, GroupNameRef;
    private String currentChatName, currentUserID, currentUserName, currentDate, currentTime;
    //private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chatroom);
        Log.d(TAG,"go to fragment chatroom");

        currentChatName = getIntent().getExtras().get("roomName").toString();
        Toast.makeText(ChatroomActivity.this, currentChatName, Toast.LENGTH_SHORT).show();

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        GroupNameRef = FirebaseDatabase.getInstance().getReference().child("Chatrooms").child(currentChatName);
        //InitializeFields();
        //GetUserInfo();
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SaveMessageInfoToDatabase();
                mMessageEditText.setText("");
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });


/*
        mUsername = ANONYMOUS;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        //TBD, db pasth: messages
        mMessageDatabaseReference = mFirebaseDatabase.getReference().child("chatroom-messages");

        // Initialize references to views
        mMessageListView = (ListView) findViewById(R.id.reyclerview_message_list);
        mMessageEditText = (EditText) findViewById(R.id.edittext_chatbox);
        mSendButton = (Button) findViewById(R.id.button_chatbox_send);

        // Initialize message ListView and its adapter
        List<Message> chatMessages = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(this, R.layout.item_message_received, chatMessages);
        mMessageListView.setAdapter(mMessageAdapter);

        // Send button sends a message and clears the EditText
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message(mMessageEditText.getText().toString(), mUsername, null);
                mMessageDatabaseReference.push().setValue(message);

                // Clear input box
                mMessageEditText.setText("");
            }
        });



         // Enable Send button when there's text to send
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message message = dataSnapshot.getValue(Message.class);
                mMessageAdapter.add(message);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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
        };
        mMessageDatabaseReference.addChildEventListener(mChildEventListener);*/
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    */
}
