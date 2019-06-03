package com.example.android.blindchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.blindchat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordResetActivity extends AppCompatActivity {

    private String TAG = "PasswordResetActivity";
    private ImageButton pwResetBack;
    private TextInputEditText pwResetUsername;
    private TextInputEditText pwResetEmail;
    private Button sendEmail;
    private FirebaseAuth mAuth;

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
                resetUserPassword();
            }
        });
    }

    private void resetUserPassword() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.sendPasswordResetEmail(pwResetEmail.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Instructions sent",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Email don't exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
