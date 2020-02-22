package com.example.degitalclassroom.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.adapter.PeopleProfileAdapter;
import com.example.degitalclassroom.adapter.TeacherAdapter;
import com.example.degitalclassroom.helper.SessionManager;
import com.example.degitalclassroom.model.Faculty;
import com.example.degitalclassroom.ui.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TeacherAdapter teacherAdapter;
    private ArrayList<Faculty> teacherArrayList = new ArrayList<>();

    private RecyclerView nTeacherRecycler, nStudentRecycler;
    private PeopleProfileAdapter peopleProfileAdapter;
    private ArrayList<Faculty> peopleArrayList = new ArrayList<>();

    private TextView nStudentName,nStudentContact,nStudentClass,nStudentCollegeAddress;

    private TextView accountLogout;
    private FirebaseAuth auth;
    private SessionManager session;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        // Session manager
        session = new SessionManager(getContext());

        accountLogout = (TextView) view.findViewById(R.id.account_logout);

        nStudentName = (TextView) view.findViewById(R.id.title_name);
        nStudentContact = (TextView) view.findViewById(R.id.content_name);
        nStudentClass = (TextView) view.findViewById(R.id.school_name);
        nStudentCollegeAddress = (TextView) view.findViewById(R.id.coll_address);


       /* nTeacherRecycler = (RecyclerView) view.findViewById(R.id.teachers_recycler);
        nTeacherRecycler.setHasFixedSize(true);
        nTeacherRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        teacherAdapter = new TeacherAdapter(getContext(), getTeacherList());
        nTeacherRecycler.setAdapter(teacherAdapter);*/

        nStudentRecycler = (RecyclerView) view.findViewById(R.id.people_recycler);
        nStudentRecycler.setHasFixedSize(true);
        nStudentRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        peopleProfileAdapter = new PeopleProfileAdapter(getContext(), getStudentList());
        nStudentRecycler.setAdapter(peopleProfileAdapter);

        accountLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                session.setLogin(false);
                session.logout();
               // startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        return view;
    }

    /*private ArrayList<Faculty> getTeacherList() {
        teacherArrayList.clear();

        Faculty faculty = new Faculty(
                "1",
                "Rahul Rahagdale",
                R.drawable.ic_user
        );
        teacherArrayList.add(faculty);

        return teacherArrayList;
    }*/

    private ArrayList<Faculty> getStudentList() {
        peopleArrayList.clear();

        Faculty faculty = new Faculty(
                "1",
                "Neha Roy"
        );
        peopleArrayList.add(faculty);

        faculty = new Faculty(
                "2",
                "Snehal Patel"
        );
        peopleArrayList.add(faculty);

        return peopleArrayList;
    }
}
