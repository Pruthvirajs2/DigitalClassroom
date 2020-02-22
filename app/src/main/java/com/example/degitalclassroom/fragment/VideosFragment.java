package com.example.degitalclassroom.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.adapter.VideoAdapter;
import com.example.degitalclassroom.model.Faculty;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideosFragment extends Fragment {

    private RecyclerView nRecyclerView;
    private VideoAdapter videoAdapter;
    private ArrayList<Faculty> videoArrayList = new ArrayList<>();

    public VideosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_videos, container, false);

        nRecyclerView = (RecyclerView) view.findViewById(R.id.videos_recycler);
        nRecyclerView.setHasFixedSize(true);
        nRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        videoAdapter = new VideoAdapter(getContext(), getVideosList());
        nRecyclerView.setAdapter(videoAdapter);

       return view;
    }

    private ArrayList<Faculty> getVideosList() {

        videoArrayList.clear();
        Faculty faculty = new Faculty(
                "1",
                "Kunal Patle",
                R.drawable.home_two
        );videoArrayList.add(faculty);

        faculty = new Faculty(
                "2",
                "Saurabh Patle",
                R.drawable.home_three
        );videoArrayList.add(faculty);

        return videoArrayList;
    }

}
