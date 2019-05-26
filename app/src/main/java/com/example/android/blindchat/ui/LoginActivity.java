package com.example.android.blindchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.blindchat.R;

public class LoginActivity extends AppCompatActivity {

    Button loginBack;
    TextInputEditText loginUsername;
    TextInputEditText loginPassword;
    TextView signupLink;
    TextView forgetPasswordLink;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBack = (Button)findViewById(R.id.btn_login_back);

        loginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loginUsername = (TextInputEditText)findViewById(R.id.login_username_input);
        loginPassword = (TextInputEditText)findViewById(R.id.login_password_input);

        signupLink = (TextView)findViewById(R.id.sign_up_link);
        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSignup();
            }
        });

        forgetPasswordLink = (TextView)findViewById(R.id.forget_password_link);
        forgetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toForget();
            }
        });

        login = (Button)findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMain();
            }
        });


    }

    private void toSignup() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
    private void toForget() {
        Intent intent = new Intent(this, PasswordResetActivity.class);
        startActivity(intent);
    }
    private void toMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
