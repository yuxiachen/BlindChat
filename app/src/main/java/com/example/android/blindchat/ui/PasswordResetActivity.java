package com.example.android.blindchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.blindchat.R;

public class PasswordResetActivity extends AppCompatActivity {

    ImageButton pwResetBack;
    TextInputEditText pwResetUsername;
    TextInputEditText pwResetEmail;
    Button sendEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        pwResetBack = (ImageButton)findViewById(R.id.btn_pw_reset_back);

        pwResetBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        pwResetUsername = (TextInputEditText)findViewById(R.id.pw_reset_username);
        pwResetEmail = (TextInputEditText)findViewById(R.id.pw_reset_email);

        sendEmail = (Button)findViewById(R.id.btn_send_email);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toInit();
            }
        });

    }

    private void toInit() {
        Intent intent = new Intent(this, InitActivity.class);
        startActivity(intent);
    }
}
