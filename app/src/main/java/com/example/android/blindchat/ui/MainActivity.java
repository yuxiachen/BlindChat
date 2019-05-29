package com.example.android.blindchat.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.blindchat.R;
import com.example.android.blindchat.model.User;




public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_trending:
                    selectedFragment = new TrendingFragment();
                    break;
                case R.id.navigation_joined:
                    selectedFragment = new JoinedRoomFragment();
                    break;
                case R.id.navigation_create:
                    selectedFragment = new CreateChatroomFragment();
                    break;
                case R.id.navigation_settings:
                    selectedFragment = new SettingFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, selectedFragment).commit();

            return true;
        }
    };

}






/***
 * Method to get the user info, can be used later*

DatabaseReference dbref = FirebaseDatabase.getInstance()
        .getReference().child("Users");

            dbref.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        user = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .getValue(User.class);
    }
    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
    }
});
 */


