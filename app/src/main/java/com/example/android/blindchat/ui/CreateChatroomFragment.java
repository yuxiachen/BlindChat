package com.example.android.blindchat.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.blindchat.R;
import com.example.android.blindchat.model.Chatroom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateChatroomFragment extends Fragment implements LocationListener {
    private EditText et_topic;
    private EditText et_description;
    private Button btn_create_chatroom;
    private String topic;
    private String description;

    //for longtitude and latitude
    private LocationManager locationManager;
    private Location location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, null);
        //for longtitude and latitude
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
        double longitude = location.getLongitude();
        Log.d("longitude::::::::::", Double.toString(longitude));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_topic = view.findViewById(R.id.topic_fragment_create);
        et_description = view.findViewById(R.id.description_fragment_create);
        btn_create_chatroom = view.findViewById(R.id.btn_create_chatroom_fragment_create);
        btn_create_chatroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInputValid()) {
                    createNewChatroom();
                }
            }
        });
    }

    private boolean checkInputValid(){
        topic = et_topic.getText().toString().trim();
        description = et_description.getText().toString().trim();
        boolean isValid = true;
        if (topic.isEmpty()) {
            et_topic.requestFocus();
            isValid = false;
            Toast.makeText(getActivity(), "Topic can not be empty!", Toast.LENGTH_LONG).show();
        }
        return isValid;
    }

    public void createNewChatroom() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String currTime = simpleDateFormat.format(new Date());
        final Chatroom newChatroom = new Chatroom(topic, description, currTime);

        final DatabaseReference newRoomRef = FirebaseDatabase.getInstance().getReference("Chatrooms").push();
        newRoomRef.setValue(newChatroom)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Chatroom created! Longitude: " + location.getLongitude()
                                    + ". Latitude: " + location.getLatitude(), Toast.LENGTH_LONG).show();
                            openChatroom(newRoomRef.getKey());
                        } else {
                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void openChatroom(String key) {
        Intent intent = new Intent(getActivity(), ChatroomActivity.class);
        intent.putExtra("key", key);
        startActivity(intent);

    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
