package com.example.android.blindchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.blindchat.R;

public class SignupActivity extends AppCompatActivity {

    Button signupBack;
    TextInputEditText email;
    TextInputEditText username;
    TextInputEditText password;
    TextInputEditText passwordConfirm;
    TextView loginLink;
    Button create;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signupBack = (Button)findViewById(R.id.btn_signup_back);
        signupBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        email = (TextInputEditText)findViewById(R.id.signup_email_input);
        username = (TextInputEditText)findViewById(R.id.signup_username_input);
        password = (TextInputEditText)findViewById(R.id.signup_password_input);
        passwordConfirm = (TextInputEditText)findViewById(R.id.signup_password_confirm);

        loginLink = (TextView)findViewById(R.id.login_link);
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLogin();
            }
        });

        create = (Button)findViewById(R.id.btn_create_account);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMain();
            }
        });
    }

    private void toLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void toMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
