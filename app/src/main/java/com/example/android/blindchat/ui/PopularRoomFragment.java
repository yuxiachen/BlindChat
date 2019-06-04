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

public class PopularRoomFragment extends Fragment implements ChatroomAdapter.OnRoomItemClickedListener{
    private RecyclerView mostPopularList;
    private ChatroomAdapter popularRoomListAdapter;
    private ArrayList<Chatroom> popularChatrooms;
    private ArrayList<String> popularChatroomKeys;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mostPopularList = view.findViewById(R.id.rv_room_list);
        mostPopularList.setHasFixedSize(true);
        mostPopularList.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        popularChatrooms = new ArrayList<>();
        popularChatroomKeys = new ArrayList<>();
        popularRoomListAdapter = new ChatroomAdapter(popularChatrooms, popularChatroomKeys, this);
        mostPopularList.setAdapter(popularRoomListAdapter);

        Query popularQuery = FirebaseDatabase.getInstance().getReference("Chatrooms").orderByChild("member_number");
        popularQuery.addListenerForSingleValueEvent(popularChatroomEventListener);

    }

    private ValueEventListener popularChatroomEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            popularChatrooms.clear();
            popularChatroomKeys.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chatroom chatroom = snapshot.getValue(Chatroom.class);
                    String key = snapshot.getKey();
                    popularChatrooms.add(chatroom);
                    popularChatroomKeys.add(key);
                }
                popularRoomListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void openChatroomActivity(String key) {
        Intent intent = new Intent(getActivity(), ChatroomActivity.class);
        intent.putExtra("key", key);
        startActivity(intent);
    }

    @Override
    public void OnRoomItemClicked(String key) {
        openChatroomActivity(key);
    }
}
