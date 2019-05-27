package com.example.android.blindchat.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.android.blindchat.R;

public class InitActivity extends AppCompatActivity {

    Button logIn;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        logIn = (Button)findViewById(R.id.btn_to_login);
        signUp = (Button)findViewById(R.id.btn_to_signup);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogIn();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignUp();
            }
        });
    }

    public void goLogIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void goSignUp() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}
