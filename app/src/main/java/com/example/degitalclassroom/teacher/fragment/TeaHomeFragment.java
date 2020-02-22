package com.example.degitalclassroom.teacher.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.helper.SessionManager;
import com.example.degitalclassroom.teacher.activity.AddClassActivity;
import com.example.degitalclassroom.ui.employee.StudentAttendanceActivity;
import com.example.degitalclassroom.ui.student.StudentsActivity;
import com.example.degitalclassroom.ui.TeacherProfileActivity;
import com.example.degitalclassroom.ui.employee.SubjectActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeaHomeFragment extends Fragment {

    private Toolbar toolbar;
    private FirebaseAuth auth;
    private SessionManager session;
    private Context context;
    private ImageView profileImageView;

    private LinearLayout nStudentLayout, nClassLayout, nAttendanceLayout, mSubjectLayout;

    public TeaHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tea_home, container, false);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        // Session manager
        session = new SessionManager(getContext());


        nStudentLayout = (LinearLayout) view.findViewById(R.id.student_cart);
        nClassLayout = (LinearLayout) view.findViewById(R.id.class_cart);
        nAttendanceLayout = (LinearLayout) view.findViewById(R.id.attendance_cart);
        mSubjectLayout = (LinearLayout) view.findViewById(R.id.subject_);

        nStudentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Intent intent = new Intent(getContext(), StudentsActivity.class);
                activity.startActivity(intent);
            }
        });

        nClassLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Intent intent = new Intent(getContext(), AddClassActivity.class);
                activity.startActivity(intent);
            }
        });

        nAttendanceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Intent intent = new Intent(getContext(), StudentAttendanceActivity.class);
                activity.startActivity(intent);
            }
        });


        profileImageView = (ImageView) view.findViewById(R.id.action_profile);
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), TeacherProfileActivity.class));
            }
        });

        mSubjectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SubjectActivity.class));
            }
        });
    }

}
