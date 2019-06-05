package com.example.android.blindchat.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.android.blindchat.R;


public class MainActivity extends AppCompatActivity {
    private Fragment selectedFragment;
    private Fragment trendingFragment;
    private Fragment joinedRoomFragment;
    private Fragment settingFragment;
    private Fragment createChatroomFragment;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        // add need to add a fragment when create the activity
        if (savedInstanceState == null) {
            if (selectedFragment == null) {
                trendingFragment = new TrendingFragment();
                selectedFragment = trendingFragment;
                fragmentManager.beginTransaction().add(R.id.main_frame, selectedFragment).commit();
            }
        } else {
            selectedFragment = getSupportFragmentManager().getFragment(savedInstanceState, "SelectFragment");
            trendingFragment = getSupportFragmentManager().getFragment(savedInstanceState, "TrendingFragment");
            joinedRoomFragment = getSupportFragmentManager().getFragment(savedInstanceState, "JoinedFragment");
            createChatroomFragment = getSupportFragmentManager().getFragment(savedInstanceState, "CreateChatroomFragment");
            settingFragment = getSupportFragmentManager().getFragment(savedInstanceState, "SettingFragment");
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_trending:
                    if (trendingFragment == null) {
                        trendingFragment = new TrendingFragment();
                    }
                    selectedFragment = trendingFragment;
                    break;
                case R.id.navigation_joined:
                    if (joinedRoomFragment == null) {
                        joinedRoomFragment = new JoinedRoomFragment();
                    }
                    selectedFragment = joinedRoomFragment;
                    break;
                case R.id.navigation_create:
                    if (createChatroomFragment == null) {
                        createChatroomFragment = new CreateChatroomFragment();
                    }
                    selectedFragment = createChatroomFragment;
                    break;
                case R.id.navigation_settings:
                    if (settingFragment == null) {
                        settingFragment = new SettingFragment();
                    }
                    selectedFragment = settingFragment;
                    break;
            }
            replaceFragment(selectedFragment);
            return true;
        }
    };

    public void replaceFragment(Fragment selectedFragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, selectedFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void toSearchFragment(String query) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        SearchFragment searchFragment = new SearchFragment();
        transaction.replace(R.id.main_frame, searchFragment);
        transaction.commit();
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (selectedFragment != null && selectedFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "SelectFragment", selectedFragment);
        }

        if (trendingFragment != null) {
            getSupportFragmentManager().putFragment(outState, "TrendingFragment", trendingFragment);
        }
        if (settingFragment != null) {
            getSupportFragmentManager().putFragment(outState, "SettingFragment", settingFragment);
        }
        if (joinedRoomFragment != null) {
            getSupportFragmentManager().putFragment(outState, "JoinedFragment", joinedRoomFragment);
        }
        if (createChatroomFragment != null) {
            getSupportFragmentManager().putFragment(outState, "CreateChatroomFragment", createChatroomFragment);
        }
    }
}


