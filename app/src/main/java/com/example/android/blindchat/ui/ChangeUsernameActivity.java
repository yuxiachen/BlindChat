package com.example.android.blindchat.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.blindchat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ChangeUsernameActivity extends AppCompatActivity {
    private EditText newUsername;
    private Button saveBtn;
    private String snewUsername;
    private ActionBar mActionBar;
    private String userKey;
    private DatabaseReference userRef;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);

        mActionBar = getSupportActionBar();
        mActionBar.setTitle("Change Username");
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        newUsername = (TextInputEditText) findViewById(R.id.change_un_input);
        saveBtn = (Button) findViewById(R.id.bt_un_save);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newUsername.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter username", Toast.LENGTH_LONG).show();
                } else {
                    updateProfile();
                }

            }
        });
    }

        public void updateProfile(){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String useruid=user.getUid();
            snewUsername = newUsername.getText().toString();
            DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(useruid);

            ref.child("username").setValue(snewUsername);
            Toast.makeText(getApplicationContext(), "Username has been updated", Toast.LENGTH_LONG).show();
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
