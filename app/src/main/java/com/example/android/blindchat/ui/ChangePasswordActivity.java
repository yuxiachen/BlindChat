package com.example.android.blindchat.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.support.constraint.Constraints.TAG;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText currentpw;
    private EditText newpw;
    private EditText newpwagain;
    private Button savepw;
    private String scurrentpw;
    private String snewpw;
    private String snewpwagain;
    private boolean isValidInput;
    private FirebaseAuth mAuth;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mActionBar = getSupportActionBar();
        mActionBar.setTitle("Change Password");
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        currentpw = (TextInputEditText) findViewById(R.id.change_un_input);
        newpw = (TextInputEditText) findViewById(R.id.change_newpw_input);
        newpwagain = (TextInputEditText) findViewById(R.id.change_newpw_input1);

        savepw =(Button)findViewById(R.id.bt_pw_save) ;

        savepw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInputIsValid();
            }
        });
    }

    private void checkInputIsValid() {
        scurrentpw = currentpw.getText().toString().trim();
        snewpw = newpw.getText().toString().trim();
        snewpwagain = newpwagain.getText().toString().trim();
        isValidInput = true;
        if (scurrentpw.isEmpty() ){
            currentpw.requestFocus();
            isValidInput = false;
            Toast.makeText(getApplicationContext(), getString(R.string.input_password_error), Toast.LENGTH_LONG).show();
        }
        if (!snewpw.equals(snewpwagain)) {
            newpwagain.requestFocus();
            isValidInput = false;
            Toast.makeText(getApplicationContext(), getString(R.string.input_password_not_consistent), Toast.LENGTH_LONG).show();
        }
        if (isValidInput) {
            updatePassword();
        }
    }

    private void updatePassword() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String email =user.getEmail();
        AuthCredential credential = EmailAuthProvider.getCredential(email,scurrentpw);

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    user.updatePassword(snewpw).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),
                                        "Password has been updated",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e(TAG, "Error in updating password",
                                        task.getException());
                                Toast.makeText(getApplicationContext(),
                                        "Failed to update password.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(),"Authentication failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
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
