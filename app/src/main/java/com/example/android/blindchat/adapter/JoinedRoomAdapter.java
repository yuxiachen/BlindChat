package com.example.android.blindchat.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.blindchat.R;
import com.example.android.blindchat.model.Chatroom;
import com.example.android.blindchat.model.JoinedRoom;
import com.example.android.blindchat.ui.ChatroomInfoActivity;

import java.util.List;

public class JoinedRoomAdapter extends ArrayAdapter<JoinedRoom> {

    private int layoutId;
    private Context context;

    public JoinedRoomAdapter(Context context, int layoutId, List<JoinedRoom> list) {
        super(context, layoutId, list);
        this.layoutId = layoutId;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        JoinedRoom room = getItem(position);
        if (convertView == null) {

            view = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.roomNumber = (TextView) view.findViewById(R.id.txt_joined_room_index);
            viewHolder.joinedSince = (TextView) view.findViewById(R.id.txt_joined_since);
            viewHolder.liveMembers = (TextView) view.findViewById(R.id.txt_members_live);
            viewHolder.topic = (TextView) view.findViewById(R.id.txt_topic);
            viewHolder.roomInfoButton = (Button)view.findViewById(R.id.btn_room_info);
            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }


        viewHolder.roomNumber.setText(room.getRoom().getName());
        viewHolder.joinedSince.setText("Joined since: " + room.joinedSince());
        viewHolder.liveMembers.setText("Members live: " + String.valueOf(room.getRoom().getMember_number()));
        viewHolder.topic.setText(room.getRoom().getName());

        viewHolder.roomInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //for room information
                Chatroom cRoom = getItem(position).getRoom();

                //go to chatroominfoactivity
                Intent intent = new Intent(context, ChatroomInfoActivity.class);
                context.startActivity(intent);

            }
        });


        return view;
    }

    private class ViewHolder{
        TextView roomNumber;
        TextView joinedSince;
        TextView liveMembers;
        TextView topic;
        Button roomInfoButton;
    }

}
