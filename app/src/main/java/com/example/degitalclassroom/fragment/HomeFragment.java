package com.example.degitalclassroom.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.adapter.FacultyHomeAdapter;
import com.example.degitalclassroom.helper.SessionManager;
import com.example.degitalclassroom.model.Faculty;
import com.example.degitalclassroom.model.User;
import com.example.degitalclassroom.ui.StudentProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private Context context;
    private FacultyHomeAdapter facultyHomeAdapter;
    private ArrayList<User> facultyArrayList = new ArrayList<>();
    private RecyclerView nRecyclerView;

    private FirebaseAuth auth;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private ImageView profileImageView;
    private String userId;
    private SessionManager session;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        // Session manager
        session = new SessionManager(getContext());

        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        profileImageView = (ImageView) view.findViewById(R.id.action_profile);

        nRecyclerView = (RecyclerView) view.findViewById(R.id.home_recycler);
        nRecyclerView.setHasFixedSize(true);
        nRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), StudentProfileActivity.class));
            }
        });
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                facultyArrayList.clear();
                if (dataSnapshot.exists()) {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        //getting User from firebase console
                        User user = postSnapshot.getValue(User.class);
                        assert user != null;
                        if (user.getDesignation().equals("Employee")) {
                            facultyArrayList.add(user);
                        }
                    }

                    facultyHomeAdapter = new FacultyHomeAdapter(getContext(), facultyArrayList);
                    nRecyclerView.setAdapter(facultyHomeAdapter);
                    facultyHomeAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
