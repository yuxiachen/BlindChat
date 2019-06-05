package com.example.android.blindchat.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.blindchat.R;
import com.example.android.blindchat.model.Chatroom;

import java.util.ArrayList;

public class ChatroomAdapter extends RecyclerView.Adapter<ChatroomAdapter.RoomItemViewHolder> implements View.OnClickListener{
    private OnRoomItemClickedListener mListener;
    private ArrayList<Chatroom> mRoomList;
    private ArrayList<String> mRoomKeys;

    public ChatroomAdapter(ArrayList<Chatroom> chatrooms, ArrayList<String> keys, OnRoomItemClickedListener listener) {
        mRoomList = chatrooms;
        mRoomKeys = keys;
        mListener = listener;
    }

    @NonNull
    @Override
    public RoomItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_room, viewGroup, false);
        RoomItemViewHolder holder = new RoomItemViewHolder(view);
        holder.itemView.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoomItemViewHolder roomItemViewHolder, int i) {
        Chatroom currChatroom = mRoomList.get(i);
        roomItemViewHolder.updateUI(currChatroom);
    }

    @Override
    public int getItemCount() {
        return mRoomList.size();
    }

    @Override
    public void onClick(View v) {
        int position = ((RecyclerView) v.getParent()).getChildAdapterPosition(v);
        mListener.OnRoomItemClicked(mRoomKeys.get(position), mRoomList.get(position).getName());
    }

    public static class RoomItemViewHolder extends RecyclerView.ViewHolder{
        private TextView roomNameTextView;
        private TextView roomMemberNumTextView;
        private TextView roomSchoolTextView;

        public RoomItemViewHolder(@NonNull View itemView) {
            super(itemView);
            roomNameTextView = itemView.findViewById(R.id.name_item_room);
            roomMemberNumTextView = itemView.findViewById(R.id.member_num_item_room);
            roomSchoolTextView = itemView.findViewById(R.id.school_item_room);
        }
        public void updateUI(Chatroom chatroom) {
            roomNameTextView.setText(chatroom.getName());
            roomMemberNumTextView.setText(Integer.toString(chatroom.getMember_number()));
            roomSchoolTextView.setText(chatroom.getSchool());
        }
    }

    public interface OnRoomItemClickedListener{
        void OnRoomItemClicked(String key, String roomName);
    }
}
