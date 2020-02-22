package com.example.degitalclassroom.ui;

import androidx.annotation.NonNull;

import com.example.degitalclassroom.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.degitalclassroom.fragment.attendanceFragment;
import com.example.degitalclassroom.fragment.HomeFragment;
import com.example.degitalclassroom.fragment.ProfileFragment;

public class StudentActivity extends AppCompatActivity {

    // private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new HomeFragment());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //   toolbar.setTitle("Home");
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_attendance:
                    // toolbar.setTitle("Chats");
                    fragment = new attendanceFragment();
                    loadFragment(fragment);
                    return true;
               /* case R.id.navigation_video:
                   // toolbar.setTitle("Videos");
                    fragment = new VideosFragment();
                    loadFragment(fragment);
                    return true;*/
               /* case R.id.navigation_profile:
                   // toolbar.setTitle("Profile");
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;*/
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

}