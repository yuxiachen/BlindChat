package com.example.android.blindchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.blindchat.R;
import com.example.android.blindchat.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private String TAG = "SignUpActivity";
    private TextInputEditText et_email;
    private TextInputEditText et_username;
    private TextInputEditText et_password;
    private TextInputEditText et_passwordConfirm;
    private TextView loginLink;
    private Button create;
    private FirebaseAuth mAuth;
    private String email;
    private String username;
    private String password;
    private String passwordConfirm;
    private Boolean isValidInput;
    private ActionBar mActionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        mActionBar = getSupportActionBar();
        mActionBar.setTitle("Log in");
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        et_email = (TextInputEditText)findViewById(R.id.signup_email_input);
        et_username = (TextInputEditText)findViewById(R.id.signup_username_input);
        et_password = (TextInputEditText)findViewById(R.id.signup_password_input);
        et_passwordConfirm = (TextInputEditText)findViewById(R.id.signup_password_confirm);

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
                checkInputIsValid();
            }
        });
    }

    public void checkInputIsValid(){
        password = et_password.getText().toString().trim();
        passwordConfirm = et_passwordConfirm.getText().toString().trim();
        username = et_username.getText().toString().trim();
        email = et_email.getText().toString().trim();
        isValidInput = true;
        if (username.isEmpty()) {
            et_username.requestFocus();
            isValidInput = false;
            Toast.makeText(getApplicationContext(), getString(R.string.input_name_error), Toast.LENGTH_LONG).show();
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.requestFocus();
            isValidInput = false;
            Toast.makeText(getApplicationContext(), getString(R.string.input_email_error), Toast.LENGTH_LONG).show();
        }
        if (password.isEmpty()) {
            et_password.requestFocus();
            isValidInput = false;
            Toast.makeText(getApplicationContext(), getString(R.string.input_password_error), Toast.LENGTH_LONG).show();
        }

        if (!password.equals(passwordConfirm)) {
            et_passwordConfirm.requestFocus();
            Toast.makeText(getApplicationContext(), getString(R.string.input_password_not_consistent), Toast.LENGTH_LONG).show();
        }

        if (isValidInput) {
            signUpNewUser(username, email, password);
        }
    }

    private void signUpNewUser(final String username, final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            final User user = new User(email, username);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(mAuth.getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Sign Up Successfully!", Toast.LENGTH_LONG).show();
                                        openMainActivity(user);
                                    } else {
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    private void toLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void openMainActivity(User user) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("User", user);
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
