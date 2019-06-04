package com.example.android.blindchat.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.blindchat.R;
import com.example.android.blindchat.model.Message;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> implements View.OnClickListener{

    private NotificationAdapter.OnNotificationClickedListener nListener;

    private ArrayList<Message> messages;
    private ArrayList<String> messageKeys;

    public NotificationAdapter(ArrayList<Message> messageList, ArrayList<String> keys, OnNotificationClickedListener listener) {
        messages = messageList;
        messageKeys = keys;
        nListener = listener;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notification, viewGroup, false);
        NotificationViewHolder holder = new NotificationViewHolder(view);
        holder.itemView.setOnClickListener(this);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder notificationViewHolder, int i) {
        Message message = messages.get(i);
        notificationViewHolder.display(message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public void onClick(View v) {
        int position = ((RecyclerView) v.getParent()).getChildAdapterPosition(v);
        nListener.OnNotificationClicked(messages.get(position), messageKeys.get(position));
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder{
        private TextView notificationTitle;
        private TextView notificationUsername;
        private TextView notificationContent;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationTitle = itemView.findViewById(R.id.notification_title);
            notificationUsername = itemView.findViewById(R.id.notification_username);
            notificationContent = itemView.findViewById(R.id.notification_content);
        }
        public void display(Message message) {
            notificationTitle.setText(message.getRoomName());
            notificationUsername.setText(message.getName());
            notificationContent.setText(message.getMessage());
        }
    }

    public interface OnNotificationClickedListener{
        void OnNotificationClicked(Message message, String key);
    }
}
