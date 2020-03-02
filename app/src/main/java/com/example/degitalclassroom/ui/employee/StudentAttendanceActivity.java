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
import com.example.degitalclassroom.model.Attendance;
import com.example.degitalclassroom.model.Subject;
import com.example.degitalclassroom.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class StudentAttendanceActivity extends AppCompatActivity {

    public ClassAttendanceAdapter attendanceAdapter;

    SubjectItemAdapter subjectItemAdapter;
    private ArrayList<Subject> mSubjects = new ArrayList<>();
    private RecyclerView subjectRecyclerView;
    ArrayList<Attendance> mSelectedAttendances = new ArrayList<>();

    private ImageView onPrevious;

    private FirebaseAuth auth;
    private DatabaseReference subjectReference, classReference, userReference, attendanceReference;
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
        subjectReference.keepSynced(true);
        attendanceReference = mFirebaseInstance.getReference("Attendance");
        attendanceReference.keepSynced(true);
        classReference = mFirebaseInstance.getReference("Classroom");
        classReference.keepSynced(true);
        userReference = mFirebaseInstance.getReference("users");
        userReference.keepSynced(true);

        subjectRecyclerView = (RecyclerView) findViewById(R.id.subject_list);

        onPrevious = (ImageView) findViewById(R.id.previous);
        onPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        subjectRecyclerView.setHasFixedSize(true);
        subjectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        subjectRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        mFirebaseInstance.getReference("users").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    final User currentUser = dataSnapshot.getValue(User.class);

                    subjectReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            mSubjects.clear();
                            if (snapshot.exists()) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                    Subject subject = dataSnapshot.getValue(Subject.class);

                                    if (subject != null) {

                                        if (currentUser.getClassName().equals(subject.getClassName())) {
                                            mSubjects.add(0, subject);
                                        }
                                    }

                                }

                                subjectItemAdapter = new SubjectItemAdapter(mSubjects, StudentAttendanceActivity.this, new OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                        //  Toast.makeText(StudentAttendanceActivity.this, mSubjects.get(position).getSubName(), Toast.LENGTH_SHORT).show();

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


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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


        SimpleDateFormat date = new SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault());
        String currentDate = date.format(new Date());

        attendanceReference.child(subject.getId()).child(currentDate).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    Log.d("dataSnapshot", "onDataChange: " + dataSnapshot.toString());
                    attendButton.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                final ArrayList<User> mStudents = new ArrayList<>();
                if (snapshot.exists()) {
                    mStudents.clear();


                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        //getting User from firebase console
                        User user = postSnapshot.getValue(User.class);

                        if (user.getDesignation().equals("Student") && subject.getClassName().equals(user.getClassName())) {
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


                 /*   try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z", Locale.getDefault());
                        String currentDateandTime = sdf.format(new Date());

                        for (User user : attendanceAdapter.mStudents) {

                            user.setCheckAttendance(true);
                            Attendance attendance = new Attendance(
                                    user.getId(),
                                    user.getUserid(),
                                    currentDateandTime,
                                    user.isCheckAttendance()

                            );

                            mSelectedAttendances.add(attendance);
                            attendanceAdapter.notifyDataSetChanged();
                        }


                    } catch (NullPointerException e) {
                        e.getStackTrace();
                    }
*/

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        attendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (attendanceAdapter.mStudents.size() > 0) {

                    for (User user : attendanceAdapter.mStudents) {

                        //getting a unique id using push().getKey() method
                        //it will create a unique id and we will use it as the Primary Key for our Subject
                        String id = attendanceReference.push().getKey();

                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z", Locale.getDefault());
                            String currentDateandTime = sdf.format(new Date());

                            Attendance attendance = new Attendance(
                                    id, user.getId(), currentDateandTime, user.isCheckAttendance()
                            );


                            SimpleDateFormat date = new SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault());
                            String currentDate = date.format(new Date());

                            attendanceReference.child(subject.getId()).child(currentDate).child(user.getId()).setValue(attendance).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(context, "Today's Attendance Genearted.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } catch (NullPointerException e) {
                            e.getStackTrace();
                        }


                    }

                }
            }
        });


        dialog.show();

    }


}
