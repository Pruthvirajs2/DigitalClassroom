package com.example.degitalclassroom.teacher.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.adapter.DashboardItemAdapter;
import com.example.degitalclassroom.helper.SessionManager;
import com.example.degitalclassroom.interfaces.OnItemClickListener;
import com.example.degitalclassroom.model.Dashboard;
import com.example.degitalclassroom.teacher.activity.AddClassActivity;
import com.example.degitalclassroom.ui.employee.AttendanceReportActivity;
import com.example.degitalclassroom.ui.employee.StudentAttendanceActivity;
import com.example.degitalclassroom.ui.student.StudentsActivity;
import com.example.degitalclassroom.ui.TeacherProfileActivity;
import com.example.degitalclassroom.ui.employee.SubjectActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherHomeFragment extends Fragment {

    private FirebaseAuth auth;
    private SessionManager session;
    RecyclerView mDashboardRecyclerView;

    DashboardItemAdapter dashboardItemAdapter;
    ArrayList<Dashboard> mDashboards = new ArrayList<>();


    ImageView profile;

    public TeacherHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teacher_home, container, false);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        // Session manager
        session = new SessionManager(getContext());


        mDashboardRecyclerView = (RecyclerView) view.findViewById(R.id.dashboard_list);
        profile = (ImageView) view.findViewById(R.id.action_profile);

        mDashboardRecyclerView.setHasFixedSize(true);
        mDashboardRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        dashboardItemAdapter = new DashboardItemAdapter(getBoardItem(), getContext(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                switch (position) {

                    case 0:
                        startActivity(new Intent(getContext(), StudentsActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getContext(), AddClassActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getContext(), StudentAttendanceActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getContext(), SubjectActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(getContext(), AttendanceReportActivity.class));
                        break;

                }

            }
        });


        mDashboardRecyclerView.setAdapter(dashboardItemAdapter);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), TeacherProfileActivity.class));
            }
        });

    }

    private ArrayList<Dashboard> getBoardItem() {

        Dashboard dashboard = new Dashboard(
                "1", "Students", R.drawable.students
        );
        mDashboards.add(dashboard);

        dashboard = new Dashboard(
                "2", "Classroom", R.drawable.classroom
        );
        mDashboards.add(dashboard);
        dashboard = new Dashboard(
                "3", "Generate Attendance", R.drawable.attendance
        );
        mDashboards.add(dashboard);
        dashboard = new Dashboard(
                "4", "Subjects", R.drawable.subjects
        );
        mDashboards.add(dashboard);
        dashboard = new Dashboard(
                "5", "Attendance Report", R.drawable.subjects
        );
        mDashboards.add(dashboard);

        return mDashboards;
    }

}
