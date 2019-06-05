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

import com.example.android.blindchat.Notification.Token;
import com.example.android.blindchat.R;
import com.example.android.blindchat.adapter.ChatroomAdapter;
import com.example.android.blindchat.adapter.JoinedRoomAdapter;
import com.example.android.blindchat.model.Chatroom;
import com.example.android.blindchat.model.User;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

public class JoinedRoomFragment extends Fragment implements JoinedRoomAdapter.OnRoomItemClickedListener{

    private static final String TAG = "debugging joinedroom";

    private DatabaseReference dfJoindRoom;
    private RecyclerView recyclerView;
    private JoinedRoomAdapter joinedRoomAdapter;
    private ArrayList<Chatroom> joinedRooms;
    private ArrayList<String> joinedRoomKeys;
    private String userName;

    //for notification
    private FirebaseUser fuser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, null);

        //for notification
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        updateToken(FirebaseInstanceId.getInstance().getToken());

        return view;
    }

    //for notification
    private void updateToken(String token) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(fuser.getUid()).setValue(token1);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_room_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        joinedRooms = new ArrayList<>();
        joinedRoomKeys = new ArrayList<>();
        joinedRoomAdapter = new JoinedRoomAdapter(joinedRooms, joinedRoomKeys, this);
        recyclerView.setAdapter(joinedRoomAdapter);

        String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Query query = FirebaseDatabase.getInstance().getReference("JoinedRooms").child(currentUserID);
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
