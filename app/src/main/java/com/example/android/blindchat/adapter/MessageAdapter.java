package com.example.android.blindchat.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.blindchat.R;
import com.example.android.blindchat.model.Message;
import com.example.android.blindchat.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    private ArrayList<Message> messages;
    private String currUsername = "";
    private DatabaseReference dfUser;
    private FirebaseAuth mAuth;
    public MessageAdapter(ArrayList<Message> messages, String currUsername) {
        this.messages = messages;
        this.currUsername = currUsername;
        updateUsername();
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        ViewHolder holder;
        if (viewType == 1) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message_sent, viewGroup, false);
            holder = new SentMessageViewHolder(view);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message_received, viewGroup, false);
            holder = new ReceivedMessageViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder viewHolder, int i) {
        viewHolder.updateUI(messages.get(i));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getName().equals(currUsername)) {
            return 1;
        } else return 2;
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        public abstract void updateUI(Message message);
    }

    //viewholder for sent message
    public final class SentMessageViewHolder extends ViewHolder {
        private TextView messageTextView;
        private TextView timeTextView;

        public SentMessageViewHolder(View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.message_item_message_sent);
            timeTextView = itemView.findViewById(R.id.time_item_message_sent);
        }
        public void updateUI(Message message) {
            if (message != null) {
                messageTextView.setText(message.getMessage());
                timeTextView.setText(message.getTime());
            }

        }
    }

    //viewholder for received message
    public final class ReceivedMessageViewHolder extends ViewHolder {
        private TextView usernameTextView;
        private TextView messageTextView;
        private TextView timeTextView;

        public ReceivedMessageViewHolder(View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.username_item_message_received);
            messageTextView = itemView.findViewById(R.id.message_item_message_received);
            timeTextView = itemView.findViewById(R.id.time_item_message_received);
        }

        public void updateUI(Message message) {
            if (message != null) {
                usernameTextView.setText(message.getName());
                messageTextView.setText(message.getMessage());
                timeTextView.setText(message.getTime());
            }
        }
    }

    public void updateUsername(){
        mAuth = FirebaseAuth.getInstance();
        dfUser = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        dfUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    currUsername = dataSnapshot.getValue(User.class).getUsername();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}