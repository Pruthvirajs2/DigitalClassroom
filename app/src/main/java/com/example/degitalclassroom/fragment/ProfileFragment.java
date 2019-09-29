package com.example.degitalclassroom.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.adapter.PeopleProfileAdapter;
import com.example.degitalclassroom.adapter.TeacherAdapter;
import com.example.degitalclassroom.model.Faculty;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private RecyclerView nTeacherRecycler,nStudentRecycler;
    private PeopleProfileAdapter peopleProfileAdapter;
    private ArrayList<Faculty> peopleArrayList = new ArrayList<>();

    private TeacherAdapter teacherAdapter;
    private ArrayList<Faculty> teacherArrayList = new ArrayList<>();

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        nTeacherRecycler = (RecyclerView) view.findViewById(R.id.teachers_recycler);
        nTeacherRecycler.setHasFixedSize(true);
        nTeacherRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        teacherAdapter = new TeacherAdapter(getContext(), getTeacherList());
        nTeacherRecycler.setAdapter(teacherAdapter);

        nStudentRecycler = (RecyclerView) view.findViewById(R.id.people_recycler);
        nStudentRecycler.setHasFixedSize(true);
        nStudentRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        peopleProfileAdapter = new PeopleProfileAdapter(getContext(), getStudentList());
        nStudentRecycler.setAdapter(peopleProfileAdapter);

        return view;
    }

    private ArrayList<Faculty> getTeacherList() {
        teacherArrayList.clear();

        Faculty faculty = new Faculty(
                "1",
                "Rahul Rahagdale",
                R.drawable.ic_user
        );teacherArrayList.add(faculty);

        return teacherArrayList;
    }

    private ArrayList<Faculty> getStudentList() {
        peopleArrayList.clear();

        Faculty faculty = new Faculty(
                "1",
                "Neha Roy"
        );peopleArrayList.add(faculty);

        faculty = new Faculty(
                "1",
                "Snehal Patel"
        );peopleArrayList.add(faculty);

        return peopleArrayList;
    }
}
