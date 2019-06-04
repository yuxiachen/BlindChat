package com.example.android.blindchat.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.blindchat.R;
import com.example.android.blindchat.adapter.ChatroomAdapter;
import com.example.android.blindchat.adapter.NotificationAdapter;
import com.example.android.blindchat.model.Chatroom;
import com.example.android.blindchat.model.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity implements NotificationAdapter.OnNotificationClickedListener {

    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;

    private ArrayList<Message> messages;
    private ArrayList<String> messageKeys;

    //for phone's light and vibration with notification
    public static final String NOTIFICATION_CHANNEL_ID = "channel_id";
    public static final String CHANNEL_NAME = "Notification Channel";
    int importance = NotificationManager.IMPORTANCE_DEFAULT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        recyclerView = findViewById(R.id.rv_notification);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        messages = new ArrayList<>();
        messageKeys = new ArrayList<>();

        notificationAdapter = new NotificationAdapter(messages, messageKeys, this);

        recyclerView.setAdapter(notificationAdapter);
        Query query = FirebaseDatabase.getInstance().getReference("Chatrooms");
        query.addListenerForSingleValueEvent(notificationEventListener);
    }

    //this is only for testing
    private ValueEventListener notificationEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chatroom chatroom = snapshot.getValue(Chatroom.class);
                    String key = snapshot.getKey();
                    int i = 0;
                    for (Message message : chatroom.getChat_history()) {

                        //afraid of messing up firebase assign room name arbitrarily
                        message.setRoomName("Room name here" + i);
                        messages.add(message);
                        i++;
                    }



                    messageKeys.add(key);

                    //don't know why, but this loop gives nullpointerexception if don't break here
                    break;
                }

                notificationAdapter.notifyDataSetChanged();

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void openChatroomActivity(Chatroom chatroom, String key) {
        Intent intent = new Intent(this, ChatroomActivity.class);
        intent.putExtra("chatroom", chatroom);
        intent.putExtra("key", key);
        startActivity(intent);
    }

    @Override
    public void OnNotificationClicked(Message message, String key) {
        openChatroomActivity(message.getRoom(), key);

        //to test the alarm of the notification
        showNotification(message, 1, key);

    }

    private void showNotification(Message message, int notificationID, String key){

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, CHANNEL_NAME, importance);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Chatroom chatroom = message.getRoom();
        Intent intent = new Intent(this, ChatroomActivity.class);
        intent.putExtra("chatroom", chatroom);
        intent.putExtra("key", key);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, notificationID, intent, PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        builder.setContentTitle(message.getRoomName())
                .setContentText(message.getName() + ":\n" + message.getMessage())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[] {
                        500,
                        500
                })
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent);

        Notification notification = builder.build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(notificationID, notification);


    }
}
