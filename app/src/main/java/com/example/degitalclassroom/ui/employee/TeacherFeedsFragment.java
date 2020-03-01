package com.example.degitalclassroom.ui.employee;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.custom.DividerItemDecoration;
import com.example.degitalclassroom.helper.SessionManager;
import com.example.degitalclassroom.model.Feeds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherFeedsFragment extends Fragment {


    ArrayList<Feeds> mFeedsArrayList = new ArrayList<>();
    FeedItemAdapter feedItemAdapter;

    private RecyclerView nRecyclerView;
    ImageView addFeedsImageView;

    private FirebaseAuth auth;
    private DatabaseReference feedsReference;
    private FirebaseDatabase mFirebaseInstance;
    private SessionManager session;


    public TeacherFeedsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teacher_feeds, container, false);


        auth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference
        feedsReference = mFirebaseInstance.getReference("Feeds");
        feedsReference.keepSynced(true);

        nRecyclerView = (RecyclerView) view.findViewById(R.id.feeds_recyclerView);
        addFeedsImageView = (ImageView) view.findViewById(R.id.addFeeds);

        addFeedsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), FeedsActivity.class));
            }
        });

        nRecyclerView.setHasFixedSize(true);
        nRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        nRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        feedsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mFeedsArrayList.clear();
                if (dataSnapshot.exists()) {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        //getting User from firebase console
                        Feeds feeds = postSnapshot.getValue(Feeds.class);

                        if (auth.getUid().equals(feeds.getSectionId())) {
                            mFeedsArrayList.add(0, feeds);

                        }

                    }

                    feedItemAdapter = new FeedItemAdapter(mFeedsArrayList, getContext());
                    nRecyclerView.setAdapter(feedItemAdapter);
                    feedItemAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

}
