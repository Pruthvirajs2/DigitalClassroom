package com.example.degitalclassroom.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.adapter.PeopleProfileAdapter;
import com.example.degitalclassroom.adapter.TeacherAdapter;
import com.example.degitalclassroom.custom.DividerItemDecoration;
import com.example.degitalclassroom.helper.SessionManager;
import com.example.degitalclassroom.interfaces.OnItemClickListener;
import com.example.degitalclassroom.model.Faculty;
import com.example.degitalclassroom.model.User;
import com.example.degitalclassroom.ui.LoginActivity;
import com.example.degitalclassroom.ui.StudentActivity;
import com.example.degitalclassroom.ui.student.StudentItemAdapter;
import com.example.degitalclassroom.ui.student.StudentsActivity;
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
public class ProfileFragment extends Fragment {


    private RecyclerView nStudentRecycler;
    private StudentItemAdapter studentItemAdapter;
    private ArrayList<User> mUserArrayList = new ArrayList<>();

    private TextView nStudentName, nStudentContact, nStudentClass, nStudentCollegeAddress;

    private TextView accountLogout;
    private FirebaseAuth auth;
    private SessionManager session;

    private DatabaseReference userReference, classmateReference;
    private FirebaseDatabase mFirebaseInstance;


    String mClassName = "";

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

        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference
        userReference = mFirebaseInstance.getReference("users");
        classmateReference = mFirebaseInstance.getReference("users");


        accountLogout = (TextView) view.findViewById(R.id.account_logout);

        nStudentName = (TextView) view.findViewById(R.id.title_name);
        nStudentContact = (TextView) view.findViewById(R.id.content_name);
        nStudentClass = (TextView) view.findViewById(R.id.school_name);
        nStudentCollegeAddress = (TextView) view.findViewById(R.id.coll_address);

        nStudentRecycler = (RecyclerView) view.findViewById(R.id.people_recycler);
        nStudentRecycler.setHasFixedSize(true);
        nStudentRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        nStudentRecycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        accountLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                session.setLogin(false);
                session.logout();
                startActivity(new Intent(getContext(), StudentActivity.class));
            }
        });


        userReference.child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final User user = dataSnapshot.getValue(User.class);
                // Check for null
                if (user == null) {
                    return;
                }
                mClassName = user.getClassName();
                nStudentName.setText(user.getUserid());
                nStudentContact.setText(user.getContact());
                nStudentClass.setText(mClassName);
                nStudentCollegeAddress.setText(user.getAddress());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        classmateReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUserArrayList.clear();
                if (snapshot.exists()) {


                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        //getting User from firebase console
                        User user = postSnapshot.getValue(User.class);
                        if (user.getDesignation().equals("Student") && !auth.getUid().equals(user.getId())
                                && mClassName.equals(user.getClassName())) {
                            mUserArrayList.add(0, user);

                        }
                    }

                    studentItemAdapter = new StudentItemAdapter(mUserArrayList, getContext(), new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                        }
                    });
                    nStudentRecycler.setAdapter(studentItemAdapter);
                    studentItemAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

}
