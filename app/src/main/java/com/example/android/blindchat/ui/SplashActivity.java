package com.example.android.blindchat.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.blindchat.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    //splash activity is the enter point of the app. Herein we check if the user has logged or not
    // if yes, then go to the main activity
}
