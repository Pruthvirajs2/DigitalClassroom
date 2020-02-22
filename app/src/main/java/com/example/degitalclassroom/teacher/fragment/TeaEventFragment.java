package com.example.degitalclassroom.teacher.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.degitalclassroom.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeaEventFragment extends Fragment {

    private Toolbar toolbar;

    public TeaEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tea_event, container, false);

        AppCompatActivity activity =(AppCompatActivity)view.getContext();
        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("Event");
        activity.setSupportActionBar(toolbar);

   return view;
    }

}
