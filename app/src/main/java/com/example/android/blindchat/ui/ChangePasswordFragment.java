package com.example.android.blindchat.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.android.blindchat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.support.constraint.Constraints.TAG;

public class ChangePasswordFragment extends Fragment {

    private ImageButton goback;
    private EditText currentpw;
    private EditText newpw;
    private EditText newpwagain;
    private Button savepw;
    private String scurrentpw;
    private String snewpw;
    private String snewpwagain;
    private boolean isValidInput;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, null);

        currentpw = (TextInputEditText) view.findViewById(R.id.change_un_input);
        newpw = (TextInputEditText) view.findViewById(R.id.change_newpw_input);
        newpwagain = (TextInputEditText) view.findViewById(R.id.change_newpw_input1);

        savepw =(Button)view.findViewById(R.id.bt_pw_save) ;

        savepw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInputIsValid();
            }
        });
        return view;
    }

    private void checkInputIsValid() {
        scurrentpw = currentpw.getText().toString().trim();
        snewpw = newpw.getText().toString().trim();
        snewpwagain = newpwagain.getText().toString().trim();
        isValidInput = true;
        if (scurrentpw.isEmpty() ){
            currentpw.requestFocus();
            isValidInput = false;
            Toast.makeText(getActivity(), getString(R.string.input_password_error), Toast.LENGTH_LONG).show();
        }
        if (!snewpw.equals(snewpwagain)) {
            newpwagain.requestFocus();
            isValidInput = false;
            Toast.makeText(getActivity(), getString(R.string.input_password_not_consistent), Toast.LENGTH_LONG).show();
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
                                Toast.makeText(getActivity(),
                                        "Password has been updated",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e(TAG, "Error in updating password",
                                        task.getException());
                                Toast.makeText(getActivity(),
                                        "Failed to update password.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(getActivity(),"Authentication failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton goback = (ImageButton)view.findViewById(R.id.btn_pw_reset_back);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });}



    }