package com.example.android.blindchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.blindchat.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ChatroomFragment extends Fragment {
    private View chatroomFragmentView;
    private ListView list_view;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_chats = new ArrayList<>();

    private DatabaseReference GroupRef;

    public ChatroomFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstancestate){
        chatroomFragmentView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        GroupRef = FirebaseDatabase.getInstance().getReference().child("Chatrooms");
        InitializeFields();
        RetrieveAndDisplayRooms();

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int postion, long id){
                String selectroom = adapterView.getItemAtPosition(postion).toString();

                Intent roomChatIntent = new Intent(getContext(), ChatroomActivity.class);
                roomChatIntent.putExtra("selectroom", selectroom);
                startActivity(roomChatIntent);
            }
        });
        return chatroomFragmentView;
    }

    private void InitializeFields(){
        list_view = chatroomFragmentView.findViewById(R.id.rv_room_list);
        list_view.setAdapter(arrayAdapter);
    }

    private void RetrieveAndDisplayRooms(){
        GroupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<>();
                Iterator iterator = dataSnapshot.getChildren().iterator();

                while(iterator.hasNext()){
                    set.add(((DataSnapshot)iterator.next()).getKey());
                }

                list_of_chats.clear();
                list_of_chats.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
