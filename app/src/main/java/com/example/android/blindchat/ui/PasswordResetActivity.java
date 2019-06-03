package com.example.android.blindchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.android.blindchat.R;

public class PasswordResetActivity extends AppCompatActivity {

    private TextInputEditText pwResetUsername;
    private TextInputEditText pwResetEmail;
    private Button sendEmail;
    private ActionBar mActionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        mActionBar = getSupportActionBar();
        mActionBar.setTitle("Sign Up");
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
