package com.example.android.blindchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.android.blindchat.R;
import com.example.android.blindchat.adapter.JoinedRoomAdapter;
import com.example.android.blindchat.model.Chatroom;
import com.example.android.blindchat.model.JoinedRoom;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class JoinedRoomFragment extends Fragment {

    private static final String TAG = "debugging joinedroom";

    private List<JoinedRoom> list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_joined, null);


        list = loadList();

        JoinedRoomAdapter adapter = new JoinedRoomAdapter(view.getContext(), R.layout.item_joined_room, list);

        ListView listView = (ListView)view.findViewById(R.id.joined_room_listview);


        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d(TAG,"si1" );

                //target chatroom
                JoinedRoom selected = list.get(position);


                Log.d(TAG,"si2" );

                //should go to target chatroom
                Intent intent = new Intent(view.getContext(), ChatroomActivity.class);
                view.getContext().startActivity(intent);
            }
        });


        return view;
    }


    //for testing layout purpose, chatroom database needed
    private List<JoinedRoom> loadList(){



        List<Chatroom> cList = new ArrayList<>();
        List<JoinedRoom> jList = new ArrayList<>();


        for (int i = 0; i < 100; i++) {

            Chatroom cRoom = new Chatroom(getResources().getString(R.string.joined_room_title) + " " + i, "Topic: " + i, "");


            cList.add(cRoom);
        }
        for (int i = 0; i < 100; i++) {

            Time temp = new Time(i);
            JoinedRoom jRoom = new JoinedRoom(cList.get(i), temp);
            jList.add(jRoom);
        }


        return jList;

    }
}
