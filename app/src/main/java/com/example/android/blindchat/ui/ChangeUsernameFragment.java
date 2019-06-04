package com.example.android.blindchat.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Patterns;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class ChangeUsernameFragment extends Fragment {

    private ImageButton goback;
    private EditText newun ;
    private Button saveun;
    private String snewun;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_username, null);

        newun = (TextInputEditText) view.findViewById(R.id.change_un_input);
        saveun = (Button)view.findViewById(R.id.bt_un_save);
        saveun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newun.length()==0){
                    Toast.makeText(getActivity(),"Please enter username", Toast.LENGTH_LONG).show();
                }
                else {
                    updateProfile();
                }

            }
        });

        goback= (ImageButton)view.findViewById(R.id.btn_un_reset_back);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    public void updateProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(snewun)
                .build();

        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>(){
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(),
                                "Username has been updated",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        });
    }
}