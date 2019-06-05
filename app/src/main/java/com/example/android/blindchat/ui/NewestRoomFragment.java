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
import android.widget.LinearLayout;

import com.example.android.blindchat.R;
import com.example.android.blindchat.adapter.ChatroomAdapter;
import com.example.android.blindchat.model.Chatroom;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewestRoomFragment extends Fragment implements ChatroomAdapter.OnRoomItemClickedListener {
    private RecyclerView newestList;
    private ChatroomAdapter newestRoomListAdapter;
    private ArrayList<Chatroom> newestChatrooms;
    private ArrayList<String> newestChatroomKeys;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newestList = view.findViewById(R.id.rv_room_list);
        newestList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        newestList.setLayoutManager(linearLayoutManager);
        newestChatrooms = new ArrayList<>();
        newestChatroomKeys = new ArrayList<>();
        newestRoomListAdapter = new ChatroomAdapter(newestChatrooms, newestChatroomKeys, this);
        newestList.setAdapter(newestRoomListAdapter);

        Query newestQuery = FirebaseDatabase.getInstance().getReference("Chatrooms").orderByChild("created_at");
        newestQuery.addListenerForSingleValueEvent(newestChatroomEventListener);
    }

    private ValueEventListener newestChatroomEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            newestChatrooms.clear();
            newestChatroomKeys.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chatroom chatroom = snapshot.getValue(Chatroom.class);
                    String key = snapshot.getKey();
                    newestChatrooms.add(chatroom);
                    newestChatroomKeys.add(key);
                }
                newestRoomListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void openChatroomActivity(String key, String roomName) {
        Intent intent = new Intent(getActivity(), ChatroomActivity.class);
        intent.putExtra("key", key);
        intent.putExtra("roomName", roomName);
        startActivity(intent);
    }

    @Override
    public void OnRoomItemClicked(String key, String roomName) {
        openChatroomActivity(key, roomName);
    }
}
