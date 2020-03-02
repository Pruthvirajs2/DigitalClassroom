package com.example.degitalclassroom.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.helper.SessionManager;
import com.example.degitalclassroom.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class TeacherProfileActivity extends AppCompatActivity {

    public static final String TAG = TeacherProfileActivity.class.getSimpleName();
    private FirebaseAuth auth;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private SessionManager session;

    private LinearLayout profile;
    private TextView nName, nEmail, nCity, className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        session = new SessionManager(TeacherProfileActivity.this);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        // Session manager
        Log.d(TAG, "onCreate: " + auth.getUid());

        profile = (LinearLayout) findViewById(R.id.teacher_logout);
        nName = (TextView) findViewById(R.id.user_name_);
        nEmail = (TextView) findViewById(R.id.email_);
        nCity = (TextView) findViewById(R.id.city_);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                session.logout();
                Log.d("check", "onClick: ");
                startActivity(new Intent(TeacherProfileActivity.this, LoginActivity.class));
                finish();
            }
        });


        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        //getting User from firebase console
                        User user = postSnapshot.getValue(User.class);
                        if (user != null) {
                            if (user.getId().contentEquals(Objects.requireNonNull(auth.getUid()))) {
                                Log.d(TAG, "onDataChange: " + user.toString());
                                nName.setText(user.getFirst_name() + " " + user.getLast_name());
                                nEmail.setText(user.getEmail());
                                nCity.setText(user.getClassName());
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
