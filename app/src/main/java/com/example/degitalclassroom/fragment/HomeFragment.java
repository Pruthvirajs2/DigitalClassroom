package com.example.degitalclassroom.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.adapter.FacultyHomeAdapter;
import com.example.degitalclassroom.model.Faculty;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private Context context;
    private FacultyHomeAdapter facultyHomeAdapter;
    private ArrayList<Faculty> facultyArrayList = new ArrayList<>();
    private RecyclerView nRecyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        nRecyclerView = (RecyclerView) view.findViewById(R.id.home_recycler);
        nRecyclerView.setHasFixedSize(true);
        nRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        facultyHomeAdapter = new FacultyHomeAdapter(getContext(), getFacultyList());
        nRecyclerView.setAdapter(facultyHomeAdapter);


        return view;
    }

    private ArrayList<Faculty> getFacultyList() {
        facultyArrayList.clear();
        Faculty faculty = new Faculty(
                "1",
                "Neha Roy"
        );facultyArrayList.add(faculty);

        faculty = new Faculty(
                "1",
                "Rahul Sharma"
        );facultyArrayList.add(faculty);

        return facultyArrayList;
    }

}
