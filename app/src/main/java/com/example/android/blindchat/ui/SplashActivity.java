package com.example.android.blindchat.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.blindchat.R;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logined();

    }

    //splash activity is the enter point of the app. Herein we check if the user has logged or not
    // if yes, then go to the main activity

    public void logined(){
        Intent intent = new Intent(this, InitActivity.class);
        startActivity(intent);
    }


}
