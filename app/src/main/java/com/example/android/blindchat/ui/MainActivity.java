package com.example.android.blindchat.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.android.blindchat.R;

public class MainActivity extends AppCompatActivity {
    //activity used to hold the navigation bar and the other five fragment
    // (Trendingfragment, SearchFragment, CreateChatroomFragment, JoinedRoomFragment, SettingFragment)

    Button trending;
    Button joined;
    Button create;
    Button settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        TrendingFragment trendingFragment = new TrendingFragment();
        transaction.add(R.id.main_frame, trendingFragment);
        transaction.commit();

        trending = (Button)findViewById(R.id.btn_tab_trending);
        trending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trending.setBackgroundColor(Color.parseColor("#f3f31d"));
                joined.setBackgroundColor(Color.parseColor("#87ceeb"));
                create.setBackgroundColor(Color.parseColor("#87ceeb"));
                settings.setBackgroundColor(Color.parseColor("#87ceeb"));

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                TrendingFragment trendingFragment = new TrendingFragment();
                transaction.replace(R.id.main_frame, trendingFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        joined = (Button)findViewById(R.id.btn_tab_joined);
        joined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trending.setBackgroundColor(Color.parseColor("#87ceeb"));
                joined.setBackgroundColor(Color.parseColor("#f3f31d"));
                create.setBackgroundColor(Color.parseColor("#87ceeb"));
                settings.setBackgroundColor(Color.parseColor("#87ceeb"));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                JoinedRoomFragment joinedFragment = new JoinedRoomFragment();
                transaction.replace(R.id.main_frame, joinedFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        create = (Button)findViewById(R.id.btn_tab_create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trending.setBackgroundColor(Color.parseColor("#87ceeb"));
                joined.setBackgroundColor(Color.parseColor("#87ceeb"));
                create.setBackgroundColor(Color.parseColor("#f3f31d"));
                settings.setBackgroundColor(Color.parseColor("#87ceeb"));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                CreateChatroomFragment createChatroomFragment = new CreateChatroomFragment();
                transaction.replace(R.id.main_frame, createChatroomFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        settings = (Button)findViewById(R.id.btn_tab_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trending.setBackgroundColor(Color.parseColor("#87ceeb"));
                joined.setBackgroundColor(Color.parseColor("#87ceeb"));
                create.setBackgroundColor(Color.parseColor("#87ceeb"));
                settings.setBackgroundColor(Color.parseColor("#f3f31d"));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                SettingFragment settingFragment = new SettingFragment();
                transaction.replace(R.id.main_frame, settingFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        trending.setBackgroundColor(Color.parseColor("#f3f31d"));
        joined.setBackgroundColor(Color.parseColor("#87ceeb"));
        create.setBackgroundColor(Color.parseColor("#87ceeb"));
        settings.setBackgroundColor(Color.parseColor("#87ceeb"));
    }

}
