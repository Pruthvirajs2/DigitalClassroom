package com.example.degitalclassroom.fragment;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.widgets.Snapshot;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.custom.DividerItemDecoration;
import com.example.degitalclassroom.helper.SessionManager;
import com.example.degitalclassroom.interfaces.OnItemClickListener;
import com.example.degitalclassroom.model.Attendance;
import com.example.degitalclassroom.model.Subject;
import com.example.degitalclassroom.model.User;
import com.example.degitalclassroom.ui.employee.SubjectActivity;
import com.example.degitalclassroom.ui.employee.SubjectItemAdapter;
import com.example.degitalclassroom.ui.student.StudentAttendanceAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class attendanceFragment extends Fragment {

    private FirebaseAuth auth;
    private DatabaseReference subjectReference, userReference, attendanceReference;
    private FirebaseDatabase mFirebaseInstance;
    private SessionManager session;


    public RecyclerView subjectRecyclerView;
    public SubjectItemAdapter subjectItemAdapter;
    public ArrayList<Subject> mSubjects = new ArrayList<>();

    public ArrayList<Attendance> mAttendances = new ArrayList<>();
    public StudentAttendanceAdapter attendanceAdapter;

    public attendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attendence, container, false);

        auth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference
        subjectReference = mFirebaseInstance.getReference("Subject");
        subjectReference.keepSynced(true);
        userReference = mFirebaseInstance.getReference("users");
        userReference.keepSynced(true);
        attendanceReference = mFirebaseInstance.getReference("Attendance");
        attendanceReference.keepSynced(true);

        initView(view);

        subjectRecyclerView.setHasFixedSize(true);
        subjectRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        subjectRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        userReference.child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final User user = dataSnapshot.getValue(User.class);
                // Check for null
                if (user == null) {
                    return;
                }

                subjectReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        mSubjects.clear();
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                Subject subject = dataSnapshot.getValue(Subject.class);

                                if (user.getClassName().equals(subject.getClassName())) {
                                    mSubjects.add(0, subject);

                                }
                            }

                            subjectItemAdapter = new SubjectItemAdapter(mSubjects, getContext(), new OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {

                                    //Toast.makeText(getContext(), mSubjects.get(position).getSubName(), Toast.LENGTH_SHORT).show();
                                    showDialog(getContext(), mSubjects.get(position));


                                }
                            });

                            subjectRecyclerView.setAdapter(subjectItemAdapter);
                            subjectItemAdapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    private void initView(View view) {

        subjectRecyclerView = (RecyclerView) view.findViewById(R.id.subject_lists);
    }


    public void showDialog(final Context context, final Subject subject) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.student_attendance_dialog);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setLayout(width, height);


        final TextView txtTitle = (TextView) dialog.findViewById(R.id.text_title);
        txtTitle.setText(subject.getSubName());
        final ImageView closeImageView = (ImageView) dialog.findViewById(R.id.close_dialog);
        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        final RecyclerView attendanceRecyclerView = (RecyclerView) dialog.findViewById(R.id.attendance_sheet);

        attendanceRecyclerView.setHasFixedSize(true);
        attendanceRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        attendanceRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        attendanceReference.child(subject.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mAttendances.clear();

                if (dataSnapshot.exists()) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        DataSnapshot snapshot = dataSnapshot1.child(Objects.requireNonNull(auth.getUid()));

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


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        dialog.show();

    }


}
