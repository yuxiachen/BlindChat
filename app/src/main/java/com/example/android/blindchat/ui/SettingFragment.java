package com.example.android.blindchat.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.blindchat.R;

public class SettingFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, null);

        Button changename = (Button)view.findViewById(R.id.change_username);
        Button changepw = (Button)view.findViewById(R.id.change_password);
        Button logout = (Button)view.findViewById(R.id.logout);
        Button appVersion = (Button)view.findViewById(R.id.app_version);
        Button update = (Button)view.findViewById(R.id.software_update);
        Button notification = (Button)view.findViewById(R.id.btn_notification1);
        Button testLocation = (Button)view.findViewById(R.id.btn_test_location);

        changename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                ChangeUsernameFragment fragment = new ChangeUsernameFragment();
                transaction.replace(R.id.main_frame,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        changepw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                ChangePasswordFragment fragment = new ChangePasswordFragment();
                transaction.replace(R.id.main_frame,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }

            private void showAlert() {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setMessage("Are you sure you want to log out?");
                dialog.setPositiveButton("LOG OUT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(),InitActivity.class);
                        startActivity(intent);
                    }
                });
                dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.create();
                dialog.show();

            }
        });

        appVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Application Version")
                        .setMessage("Beta 1.0")
                        .setCancelable(false)
                        .create();

                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(true);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                        .setTitle("New Updates")
                        .setMessage(R.string.New_Updates)
                        .setCancelable(false)
                        .create();

                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(true);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),NotificationActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

}
