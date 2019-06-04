package com.example.android.blindchat.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.android.blindchat.R;
import com.example.android.blindchat.adapter.ChatroomAdapter;
import com.example.android.blindchat.model.Chatroom;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TrendingFragment extends Fragment implements ChatroomAdapter.OnRoomItemClickedListener {

    private SearchView searchInTrending;
    private RecyclerView mostPopularList;
    private RecyclerView newestList;
    private LinearLayout frame;
    private ChatroomAdapter popularRoomListAdapter;
    private ChatroomAdapter newestRoomListAdapter;
    private ArrayList<Chatroom> popularChatrooms;
    private ArrayList<String> popularChatroomKeys;
    private ArrayList<Chatroom> newestChatrooms;
    private ArrayList<String> newestChatroomKeys;
    private DatabaseReference dfChatroom;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trending, null);

        //to remove cursor from search bar
        frame = (LinearLayout)view.findViewById(R.id.fragment_trending);
        frame.requestFocus();

        searchInTrending = (SearchView)view.findViewById(R.id.search_in_trending);

        //target string to search
        CharSequence query = searchInTrending.getQuery();

        searchInTrending.setIconifiedByDefault(false);
        searchInTrending.setQueryHint("Search");
        searchInTrending.setSubmitButtonEnabled(true);
        searchInTrending.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ((MainActivity)getActivity()).toSearchFragment();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        dfChatroom = FirebaseDatabase.getInstance().getReference("Chatrooms");
        mostPopularList = view.findViewById(R.id.list_most_popular);
        mostPopularList.setHasFixedSize(true);
        mostPopularList.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        popularChatrooms = new ArrayList<>();
        popularChatroomKeys = new ArrayList<>();
        popularRoomListAdapter = new ChatroomAdapter(popularChatrooms, popularChatroomKeys, this);
        mostPopularList.setAdapter(popularRoomListAdapter);

        newestList = view.findViewById(R.id.list_newest);
        newestList.setHasFixedSize(true);
        newestList.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        newestChatrooms = new ArrayList<>();
        newestChatroomKeys = new ArrayList<>();
        newestRoomListAdapter = new ChatroomAdapter(newestChatrooms, newestChatroomKeys, this);
        newestList.setAdapter(newestRoomListAdapter);


        Query popularQuery = FirebaseDatabase.getInstance().getReference("Chatrooms").orderByChild("member_number");
        popularQuery.addListenerForSingleValueEvent(popularChatroomEventListener);

        Query newestQuery = FirebaseDatabase.getInstance().getReference("Chatrooms").orderByChild("created_at");
        newestQuery.addListenerForSingleValueEvent(newestChatroomEventListener);
        return view;
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
