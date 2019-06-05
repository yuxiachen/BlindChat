package com.example.android.blindchat.ui;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.android.blindchat.R;
import com.example.android.blindchat.adapter.ChatroomAdapter;
import com.example.android.blindchat.model.Chatroom;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TrendingFragment extends Fragment implements ChatroomAdapter.OnRoomItemClickedListener{

    private SearchView searchInTrending;
    private ConstraintLayout frame;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Fragment mPopularFragment;
    private Fragment mNewestFragment;
    private TextView searchTitle;
    private RecyclerView recyclerView;
    private ChatroomAdapter mAdapter;

    private ArrayList<Chatroom> resultChatrooms;
    private ArrayList<String> chatroomKeys;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trending, null);

        //to remove cursor from search bar
        frame = view.findViewById(R.id.fragment_trending);
        frame.requestFocus();

        searchInTrending = (SearchView)view.findViewById(R.id.search_in_trending);
        mTabLayout = view.findViewById(R.id.tl_tab_fragment_trending);
        mViewPager = view.findViewById(R.id.vp_room_list);

        searchTitle = view.findViewById(R.id.search_result_title);
        //mAdapter = new ChatroomAdapter();
        recyclerView = view.findViewById(R.id.search_result_list);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        resultChatrooms = new ArrayList<>();
        chatroomKeys = new ArrayList<>();
        mAdapter = new ChatroomAdapter(resultChatrooms, chatroomKeys, this);
        recyclerView.setAdapter(mAdapter);


        //target string to search
        CharSequence query = searchInTrending.getQuery();

        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new RoomListPagerAdapter(getChildFragmentManager()));


        searchInTrending.setIconifiedByDefault(false);
        searchInTrending.setQueryHint("Search");
        searchInTrending.setSubmitButtonEnabled(true);
        searchInTrending.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Query queryResult = FirebaseDatabase.getInstance().getReference("Chatrooms")
                        .orderByChild("name").startAt(query).endAt(query + "\uf8ff");
                queryResult.addListenerForSingleValueEvent(resultListener);
                mTabLayout.setVisibility(View.GONE);
                mViewPager.setVisibility(View.GONE);
                searchTitle.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    searchTitle.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    mTabLayout.setVisibility(View.VISIBLE);
                    mViewPager.setVisibility(View.VISIBLE);
                }

                return false;
            }
        });

        return view;
    }


    private ValueEventListener resultListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            resultChatrooms.clear();
            chatroomKeys.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chatroom chatroom = snapshot.getValue(Chatroom.class);
                    String key = snapshot.getKey();
                    resultChatrooms.add(chatroom);
                    chatroomKeys.add(key);
                }
                mAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    protected class RoomListPagerAdapter extends FragmentPagerAdapter {
        public RoomListPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int i) {
            switch(i) {
                case 0:
                    mPopularFragment = new PopularRoomFragment();
                    return mPopularFragment;
                case 1:
                    mNewestFragment = new NewestRoomFragment();
                    return mNewestFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0:
                    return "Popular";
                case 1:
                    return "Newest";
                default:
                    return null;
            }
        }

    }

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
