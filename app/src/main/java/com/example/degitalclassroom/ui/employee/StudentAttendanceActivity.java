package com.example.degitalclassroom.ui.employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.adapter.ClassAttendanceAdapter;
import com.example.degitalclassroom.custom.DividerItemDecoration;
import com.example.degitalclassroom.helper.SessionManager;
import com.example.degitalclassroom.interfaces.OnItemClickListener;
import com.example.degitalclassroom.model.Subject;
import com.example.degitalclassroom.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class StudentAttendanceActivity extends AppCompatActivity {

    private ClassAttendanceAdapter attendanceAdapter;

    SubjectItemAdapter subjectItemAdapter;
    private ArrayList<Subject> mSubjects = new ArrayList<>();
    private ArrayList<User> mUsers = new ArrayList<>();
    private RecyclerView subjectRecyclerView;

    private ImageView onPrevious;

    private FirebaseAuth auth;
    private DatabaseReference subjectReference, classReference, userReference;
    private FirebaseDatabase mFirebaseInstance;
    private SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);


        auth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference
        subjectReference = mFirebaseInstance.getReference("Subject");
        classReference = mFirebaseInstance.getReference("Classroom");
        userReference = mFirebaseInstance.getReference("users");

        subjectRecyclerView = (RecyclerView) findViewById(R.id.subject_list);

        onPrevious = (ImageView) findViewById(R.id.previous);
        onPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        subjectRecyclerView.setHasFixedSize(true);
        subjectRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        subjectRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        subjectReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                mSubjects.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        Subject subject = dataSnapshot.getValue(Subject.class);
                        mSubjects.add(0, subject);
                    }

                    subjectItemAdapter = new SubjectItemAdapter(mSubjects, StudentAttendanceActivity.this, new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            Toast.makeText(StudentAttendanceActivity.this, mSubjects.get(position).getSubName(), Toast.LENGTH_SHORT).show();

                            showDialog(StudentAttendanceActivity.this, mSubjects.get(position));

                        }
                    });

                    subjectRecyclerView.setAdapter(subjectItemAdapter);
                    subjectItemAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(StudentAttendanceActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void showDialog(final Context context, final Subject subject) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_attendance_dialog_item);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setLayout(width, height);

        final ImageView closeImageView = (ImageView) dialog.findViewById(R.id.close_dialog);
        final Button attendButton = (Button) dialog.findViewById(R.id.updateAttendance);

        final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.attendance_list);

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                final ArrayList<User> mStudents = new ArrayList<>();
                if (snapshot.exists()) {
                    mStudents.clear();


                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        //getting User from firebase console
                        User user = postSnapshot.getValue(User.class);

                        if (Objects.requireNonNull(user).getClassName() != null) {
                            mStudents.add(0, user);

                        }
                    }

                    attendanceAdapter = new ClassAttendanceAdapter(mStudents, context, new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {




                        }
                    });


                    recyclerView.setAdapter(attendanceAdapter);
                    attendanceAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        dialog.show();

    }


}
