package com.example.degitalclassroom.ui.employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.custom.DividerItemDecoration;
import com.example.degitalclassroom.interfaces.OnItemClickListener;
import com.example.degitalclassroom.model.Attendance;
import com.example.degitalclassroom.model.Subject;
import com.example.degitalclassroom.model.User;
import com.example.degitalclassroom.ui.student.StudentAttendanceAdapter;
import com.example.degitalclassroom.ui.student.StudentItemAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AttendanceReportActivity extends AppCompatActivity {
    public SubjectItemAdapter subjectItemAdapter;
    private ArrayList<Subject> mSubjects = new ArrayList<>();
    private FirebaseAuth auth;
    private DatabaseReference subjectReference, classReference, userReference, attendanceReference;
    private FirebaseDatabase mFirebaseInstance;
    private RecyclerView subjectRecyclerView, mStudentsRecyclerView;
    private ImageView onPrevious;
    public StudentItemAdapter studentItemAdapter;


    public ArrayList<Attendance> mAttendances = new ArrayList<>();
    public StudentAttendanceAdapter attendanceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_report);

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
        mStudentsRecyclerView = (RecyclerView) findViewById(R.id.student_Lists);

        onPrevious = (ImageView) findViewById(R.id.previous);
        onPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //classroom subject lists
        subjectRecyclerView.setHasFixedSize(true);
        subjectRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        subjectRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //classroom student lists
        mStudentsRecyclerView.setHasFixedSize(true);
        mStudentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mStudentsRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

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

                                subjectItemAdapter = new SubjectItemAdapter(mSubjects, AttendanceReportActivity.this, new OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        showStudentsFrom(AttendanceReportActivity.this, mSubjects.get(position));
                                    }
                                });

                                subjectRecyclerView.setAdapter(subjectItemAdapter);
                                subjectItemAdapter.notifyDataSetChanged();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText(AttendanceReportActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void showStudentsFrom(final Context context, final Subject subject) {

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

                    studentItemAdapter = new StudentItemAdapter(mStudents, context, new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            showDialog(AttendanceReportActivity.this, subject, mStudents.get(position));
                        }
                    });
                    mStudentsRecyclerView.setAdapter(studentItemAdapter);
                    studentItemAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void showDialog(final Context context, final Subject subject, final User user) {
        final Dialog dialog = new Dialog(context,R.style.FullScreenDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.student_attendance_dialog);

      /*
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setLayout(width, height);*/


        final TextView txtTitle = (TextView) dialog.findViewById(R.id.text_title);
        final TextView txtStudentName = (TextView) dialog.findViewById(R.id.student_name);
        txtStudentName.setVisibility(View.VISIBLE);
        txtStudentName.setText(user.getUserid());
        txtTitle.setText(subject.getSubName());
        final ImageView closeImageView = (ImageView) dialog.findViewById(R.id.close_dialog);
        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        final TextView mTextPresent = (TextView) dialog.findViewById(R.id.total_present);
        final TextView mTextAbsent = (TextView) dialog.findViewById(R.id.total_absent);
        final TextView mTextTotal = (TextView) dialog.findViewById(R.id.total_average);

        final RecyclerView attendanceRecyclerView = (RecyclerView) dialog.findViewById(R.id.attendance_sheet);

        attendanceRecyclerView.setHasFixedSize(true);
        attendanceRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        attendanceRecyclerView.addItemDecoration(new DividerItemDecoration(AttendanceReportActivity.this, LinearLayoutManager.VERTICAL));

        attendanceReference.child(subject.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mAttendances.clear();

                if (dataSnapshot.exists()) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        DataSnapshot snapshot = dataSnapshot1.child(Objects.requireNonNull(user.getId()));

                        if (snapshot.exists()) {
                            Attendance attendance = snapshot.getValue(Attendance.class);

                            if (attendance != null) {

                           /* String id = snapshot.child("id").getValue().toString();
                            String title = snapshot.child("date").getValue().toString();
                            Log.d("title", "onDataChange: " + attendance);
                            */
                                mAttendances.add(attendance);
                            }

                        }

                    }
                    attendanceAdapter = new StudentAttendanceAdapter(mAttendances, context);
                    attendanceRecyclerView.setAdapter(attendanceAdapter);
                    attendanceAdapter.notifyDataSetChanged();

                    Log.d("at_", "total: " + mAttendances.size());

                    List<String> mPresentDay = new ArrayList<>();

                    for (Attendance attendance : mAttendances) {
                        if (attendance.isCheckAttendance()) {
                            mPresentDay.add(attendance.getId());
                        }
                    }

                    long absent = mAttendances.size() - mPresentDay.size();

                    Log.d("at_", "total: Present " + mPresentDay.size());
                    Log.d("at_", "total: Absent " + absent);

                    long presentDays = mPresentDay.size();
                    long absentDays = mAttendances.size() - mPresentDay.size();
                    long attend = 100 - absentDays;

                    mTextPresent.setText(String.valueOf(presentDays));
                    mTextAbsent.setText(String.valueOf(absentDays));
                    mTextTotal.setText(String.valueOf(attend) + "%");

                    Log.d("at_", "Calculation: Absent " + absentDays + "%");
                    Log.d("at_", "Calculation: attendance " + attend + "%");

                    /*
Total Contact Hour : 36
Day Absent : 1 (Credit Hour) x 1 Day = 1 Contact Hour (Absent)
Contact Hour Recorded : 1
Total Contact Hour : 36
---------------------------------------------
Calculation
1/36 x 100% = 2.778%
100% - 2.778% = 97%

System will automatically deduct 2.778% from 100%. Therefore, student's attendance shown as 97%.

*/

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        dialog.show();

    }

}
