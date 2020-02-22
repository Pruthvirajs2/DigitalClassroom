package com.example.degitalclassroom.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.helper.SessionManager;
import com.example.degitalclassroom.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentProfileActivity extends AppCompatActivity {

    public static final String TAG = StudentProfileActivity.class.getSimpleName();

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private SessionManager session;
    private FirebaseAuth auth;

    private RelativeLayout profileLogout;

    private TextView nStudentName,nStudentEmail,nStudentCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        // Session manager
        session = new SessionManager(getApplicationContext());

        String userId = auth.getUid();
        Log.d(TAG, "onCreate: " + userId);

        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: " + dataSnapshot.getValue(User.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        profileLogout = (RelativeLayout) findViewById(R.id.student_logout);
        profileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                auth.signOut();
                session.setLogin(false);
                session.logout();
                startActivity(new Intent(StudentProfileActivity.this, LoginActivity.class));
                finish();
            }
        });

        nStudentName = (TextView) findViewById(R.id.user_name);
        nStudentEmail = (TextView) findViewById(R.id.email);
        nStudentCity = (TextView) findViewById(R.id.city);

    }
}
