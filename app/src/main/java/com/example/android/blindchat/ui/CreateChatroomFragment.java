package com.example.android.blindchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.blindchat.R;
import com.example.android.blindchat.model.Chatroom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateChatroomFragment extends Fragment {
    private EditText et_topic;
    private EditText et_description;
    private Button btn_create_chatroom;
    private String topic;
    private String description;
    private String currUid;
    private DatabaseReference newRoomRef;
    private DatabaseReference joinedUserRef;
    private DatabaseReference joinedRoomRef;
    private String roomKey;
    private Spinner schoolSpinner;
    private String school;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_topic = view.findViewById(R.id.topic_fragment_create);
        et_description = view.findViewById(R.id.description_fragment_create);
        btn_create_chatroom = view.findViewById(R.id.btn_create_chatroom_fragment_create);
        schoolSpinner = view.findViewById(R.id.school_fragment_create);
        currUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        btn_create_chatroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInputValid()) {
                    createNewChatroom();
                }
            }
        });
    }

    private boolean checkInputValid(){
        topic = et_topic.getText().toString().trim();
        description = et_description.getText().toString().trim();
        boolean isValid = true;
        if (topic.isEmpty()) {
            et_topic.requestFocus();
            isValid = false;
            Toast.makeText(getActivity(), "Topic can not be empty!", Toast.LENGTH_LONG).show();
        }
        return isValid;
    }


    public void createNewChatroom() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String currTime = simpleDateFormat.format(new Date());

        newRoomRef = FirebaseDatabase.getInstance().getReference("Chatrooms").push();
        roomKey = newRoomRef.getKey();
        final Chatroom newChatroom = new Chatroom(roomKey, topic, description, currTime);
        newChatroom.setMember_number(1);
        school = schoolSpinner.getSelectedItem().toString();
        newChatroom.setSchool(school);
        newRoomRef.setValue(newChatroom)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Chatroom created!", Toast.LENGTH_LONG).show();
                            addUserToJoinedList();
                            addRoomToJoinedList(newChatroom);
                            openChatroom(roomKey, topic);
                        } else {
                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void addUserToJoinedList() {
        joinedUserRef = FirebaseDatabase.getInstance().getReference("JoinedUsers").child(roomKey);
        joinedUserRef.child(currUid).setValue(currUid);
    }

    private void addRoomToJoinedList(Chatroom chatroom) {
        joinedRoomRef = FirebaseDatabase.getInstance().getReference("JoinedRooms").child(currUid);
        joinedRoomRef.child(roomKey).setValue(chatroom);
    }

    private void openChatroom(String key, String topic) {
        Intent intent = new Intent(getActivity(), ChatroomActivity.class);
        intent.putExtra("key", key);
        intent.putExtra("roomName", topic);
        startActivity(intent);
    }

}
