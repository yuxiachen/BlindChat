package com.example.android.blindchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.android.blindchat.R;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
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
                    replaceFragment(selectedFragment);
                    break;
                case R.id.navigation_joined:
                    selectedFragment = new JoinedRoomFragment();
                    replaceFragment(selectedFragment);
                    break;
                case R.id.navigation_create:
                    selectedFragment = new CreateChatroomFragment();
                    replaceFragment(selectedFragment);
                    break;
                case R.id.navigation_settings:
                    selectedFragment = new SettingFragment();
                    replaceFragment(selectedFragment);
                    break;
            }

            return true;
        }
        public void replaceFragment(Fragment selectedFragment){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_frame, selectedFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    };
    public void toSearchFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        SearchFragment searchFragment = new SearchFragment();
        transaction.replace(R.id.main_frame, searchFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void toRoomInfo(){
        Intent intent = new Intent(this, ChatroomInfoActivity.class);
        startActivity(intent);
    }

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


