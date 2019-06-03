package com.example.android.blindchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.android.blindchat.R;
import com.example.android.blindchat.adapter.ChatroomAdapter;
import com.example.android.blindchat.model.Chatroom;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JoinedRoomFragment extends Fragment implements ChatroomAdapter.OnRoomItemClickedListener{

    private static final String TAG = "debugging joinedroom";

    private RecyclerView recyclerView;
    private ChatroomAdapter joinedRoomAdapter;

    private ArrayList<Chatroom> joinedRooms;
    private ArrayList<String> joinedRoomKeys;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_joined, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_joined_room);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        joinedRooms = new ArrayList<>();
        joinedRoomKeys = new ArrayList<>();
        joinedRoomAdapter = new ChatroomAdapter(joinedRooms, joinedRoomKeys, this);
        recyclerView.setAdapter(joinedRoomAdapter);
        Query query = FirebaseDatabase.getInstance().getReference("Chatrooms");
        query.addListenerForSingleValueEvent(joinedRoomValueEventListener);
    }

    private ValueEventListener joinedRoomValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            joinedRooms.clear();
            joinedRoomKeys.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chatroom chatroom = snapshot.getValue(Chatroom.class);
                    String key = snapshot.getKey();
                    joinedRooms.add(chatroom);
                    joinedRoomKeys.add(key);
                }
                joinedRoomAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    private void openChatroomActivity(Chatroom chatroom, String key) {
        Intent intent = new Intent(getActivity(), ChatroomActivity.class);
        intent.putExtra("chatroom", chatroom);
        intent.putExtra("key", key);
        startActivity(intent);
    }

    @Override
    public void OnRoomItemClicked(Chatroom chatroom, String key) {
        openChatroomActivity(chatroom, key);
    }
}
