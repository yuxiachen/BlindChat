package com.example.android.blindchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.blindchat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    private TextInputEditText loginEmail;
    private TextInputEditText loginPassword;
    private TextView signupLink;
    private TextView forgetPasswordLink;
    private Button login;
    private ActionBar mActionBar;
    private Boolean isValidInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mActionBar = getSupportActionBar();
        mActionBar.setTitle("Log in");
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);


        loginEmail = (TextInputEditText)findViewById(R.id.login_email_input);
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
                if (checkInputIsValid()) {
                    signInWithEmail();
                }
            }
        });

    }

    private boolean checkInputIsValid() {
        String password = loginPassword.getText().toString().trim();
        String email = loginEmail.getText().toString().trim();
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginEmail.requestFocus();
            Toast.makeText(getApplicationContext(), getString(R.string.input_email_error), Toast.LENGTH_LONG).show();
            return false;
        }

        if (password.isEmpty()) {
            loginPassword.requestFocus();
            isValidInput = false;
            Toast.makeText(getApplicationContext(), getString(R.string.input_password_error), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void signInWithEmail(){
        mAuth.signInWithEmailAndPassword(loginEmail.getText().toString(), loginPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            toMain();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();

                        }
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
