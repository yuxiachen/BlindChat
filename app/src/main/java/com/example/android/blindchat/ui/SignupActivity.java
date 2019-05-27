package com.example.android.blindchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.blindchat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    private String TAG = "SignUpActivity";
    private Button signupBack;
    private TextInputEditText email;
    private TextInputEditText username;
    private TextInputEditText password;
    private TextInputEditText passwordConfirm;
    private TextView loginLink;
    private Button create;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
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
                if (password.getText().toString().equals(passwordConfirm.getText().toString())) {
                    signUpNewUser();
                } else {
                    Toast.makeText(getApplicationContext(), "Password are not consistent.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void toLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void toMain(FirebaseUser user) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    private void signUpNewUser() {
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            toMain(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "create User with Email Failed.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


}
