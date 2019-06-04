package com.example.android.blindchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.android.blindchat.R;
import com.example.android.blindchat.adapter.ChatroomAdapter;
import com.example.android.blindchat.model.Chatroom;
import com.example.android.blindchat.model.User;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class JoinedRoomFragment extends Fragment implements ChatroomAdapter.OnRoomItemClickedListener{

    private static final String TAG = "debugging joinedroom";

    private RecyclerView recyclerView;
    private ChatroomAdapter joinedRoomAdapter;

    private ArrayList<Chatroom> joinedRooms;
    private ArrayList<String> joinedRoomKeys;
    private DatabaseReference usersRef;
    private String userName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_room_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        joinedRooms = new ArrayList<>();
        joinedRoomKeys = new ArrayList<>();
        joinedRoomAdapter = new ChatroomAdapter(joinedRooms, joinedRoomKeys, this);
        recyclerView.setAdapter(joinedRoomAdapter);
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        usersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    userName = dataSnapshot.getValue(User.class).getUsername();
                    Log.d("NYWANG user name", userName);
                }
                Query query = FirebaseDatabase.getInstance().getReference("Chatrooms");
                query.addListenerForSingleValueEvent(joinedRoomValueEventListener);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
                    boolean joined = false;
                    for (User user : chatroom.getJoined_users()) {
                        if (user.getUsername().equals(userName)) {
                            joined = true;
                        }
                    }
                    if (joined) {
                        joinedRooms.add(chatroom);
                        joinedRoomKeys.add(key);
                    }

                }
                joinedRoomAdapter.notifyDataSetChanged();
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
