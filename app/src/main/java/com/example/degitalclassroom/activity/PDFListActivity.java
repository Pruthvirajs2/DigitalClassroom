package com.example.degitalclassroom.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.adapter.PdfListAdapter;
import com.example.degitalclassroom.custom.DividerItemDecoration;
import com.example.degitalclassroom.helper.SessionManager;
import com.example.degitalclassroom.model.Faculty;
import com.example.degitalclassroom.model.Feeds;
import com.example.degitalclassroom.model.User;
import com.example.degitalclassroom.ui.employee.FeedItemAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PDFListActivity extends AppCompatActivity {

    private RecyclerView nRecyclerView;
    private ArrayList<Feeds> mFeeds = new ArrayList<>();
    FeedItemAdapter feedItemAdapter;
    private ImageView nBackImage;
    private TextView nFacultyName;

    String userID = "";


    private FirebaseAuth auth;
    private DatabaseReference userReference, feedsReference;
    private FirebaseDatabase mFirebaseInstance;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdflist);

        if (getIntent().getExtras() != null) {
            userID = getIntent().getExtras().getString("userid");
            Log.d("userID", "onCreate: " + userID);
        }

        auth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference
        userReference = mFirebaseInstance.getReference("users");
        feedsReference = mFirebaseInstance.getReference("Feeds");


        nFacultyName = (TextView) findViewById(R.id.faculty_name_txt);

        nRecyclerView = (RecyclerView) findViewById(R.id.pdf_recycler);
        nRecyclerView.setHasFixedSize(true);
        nRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        nRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        nBackImage = (ImageView) findViewById(R.id.back_image);
        nBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });


        // User data change listener
        userReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                // Check for null
                if (user == null) {
                    return;
                }

                nFacultyName.setText(user.getFirst_name());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        feedsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mFeeds.clear();
                if (dataSnapshot.exists()) {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        //getting User from firebase console
                        Feeds feeds = postSnapshot.getValue(Feeds.class);

                        if (userID.equals(feeds.getSectionId())) {
                            mFeeds.add(0, feeds);
                        }
                    }

                    feedItemAdapter = new FeedItemAdapter(mFeeds, PDFListActivity.this);
                    nRecyclerView.setAdapter(feedItemAdapter);
                    feedItemAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
