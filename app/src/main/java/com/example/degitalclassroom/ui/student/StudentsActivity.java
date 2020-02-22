package com.example.degitalclassroom.ui.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.MacAddress;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.activity.DetailsPdfActivity;
import com.example.degitalclassroom.helper.SessionManager;
import com.example.degitalclassroom.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentsActivity extends AppCompatActivity {


    private RecyclerView mStudentRecyclerView;

    StudentItemAdapter studentItemAdapter;
    private ImageView mPreviosiView;

    private FirebaseAuth auth;
    private DatabaseReference subjectReference, classReference, usersReference;
    private FirebaseDatabase mFirebaseInstance;
    private SessionManager session;


    ArrayList<User> mUserArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        auth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference
        subjectReference = mFirebaseInstance.getReference("Subject");
        usersReference = mFirebaseInstance.getReference("users");
        classReference = mFirebaseInstance.getReference("Classroom");


        initViews();


        mPreviosiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mStudentRecyclerView.setHasFixedSize(true);
        mStudentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUserArrayList.clear();
                if (snapshot.exists()) {


                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        //getting User from firebase console
                        User user = postSnapshot.getValue(User.class);
                        if (user != null && user.getClassName() != null) {
                            mUserArrayList.add(0, user);

                        }
                    }

                    studentItemAdapter = new StudentItemAdapter(mUserArrayList, StudentsActivity.this);
                    mStudentRecyclerView.setAdapter(studentItemAdapter);
                    studentItemAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(StudentsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initViews() {

        mStudentRecyclerView = (RecyclerView) findViewById(R.id.student_list);
        mPreviosiView = (ImageView) findViewById(R.id.back_image);


    }
}
