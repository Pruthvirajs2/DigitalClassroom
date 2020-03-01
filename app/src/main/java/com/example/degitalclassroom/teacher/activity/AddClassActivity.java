package com.example.degitalclassroom.teacher.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.adapter.ClassroomAdapter;
import com.example.degitalclassroom.model.Classroom;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddClassActivity extends AppCompatActivity {

    private TextInputEditText inputClassroom;
    private TextInputLayout layoutClassroom;
    private RecyclerView mClassroomRecyclerView;
    private List<Classroom> mClassrooms = new ArrayList<>();
    private ClassroomAdapter mClassroomAdapter;
    private Button nSave;

    ImageView closeImageView;
    private SpinKitView progressBar;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("Classroom");

        initViews();

        nSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {

                    progressBar.setVisibility(View.VISIBLE);
                    final String className = inputClassroom.getText().toString();
                    final String key = mFirebaseDatabase.push().getKey();

                    mFirebaseDatabase.orderByChild("classroom").equalTo(className).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                //classroom exist
                                progressBar.setVisibility(View.GONE);
                                layoutClassroom.setError("Class " + className + " already exists.");

                            } else {
                                layoutClassroom.setError(null);
                                progressBar.setVisibility(View.GONE);

                                //classroom does not exist
                                Classroom classroom = new Classroom(key, className);
                                mFirebaseDatabase.child(key).setValue(classroom).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressBar.setVisibility(View.GONE);
                                        if (task.isComplete()) {
                                            inputClassroom.setText("");
                                            closeKeyboard();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(AddClassActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            progressBar.setVisibility(View.GONE);
                        }
                    });


                }
            }
        });


        mClassroomRecyclerView.setHasFixedSize(true);
        mClassroomRecyclerView.setLayoutManager(new LinearLayoutManager(AddClassActivity.this));
        mClassroomAdapter = new ClassroomAdapter(mClassrooms, AddClassActivity.this);
        mClassroomRecyclerView.setAdapter(mClassroomAdapter);

        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mClassrooms.clear();
                if (snapshot.exists()) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        Classroom classroom = dataSnapshot.getValue(Classroom.class);
                        mClassrooms.add(classroom);
                    }
                    mClassroomAdapter = new ClassroomAdapter(mClassrooms, AddClassActivity.this);
                    mClassroomRecyclerView.setAdapter(mClassroomAdapter);
                    mClassroomAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    //this method is used to connect XML views to its Objects
    private void initViews() {
        inputClassroom = (TextInputEditText) findViewById(R.id.editTextClassName);
        layoutClassroom = (TextInputLayout) findViewById(R.id.textInputLayoutClass);
        mClassroomRecyclerView = (RecyclerView) findViewById(R.id.classroom_list);
        progressBar = (SpinKitView) findViewById(R.id.spin_kit);
        nSave = (Button) findViewById(R.id.add_class);
        closeImageView = (ImageView) findViewById(R.id.back_image);
    }

    //This method is used to validate input given by user
    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String className = inputClassroom.getText().toString();

        //Handling validation for UserName field
        if (className.isEmpty()) {
            valid = false;
            layoutClassroom.setError("Please enter valid name!");
        } else {
            if (className.length() > 2) {
                valid = true;
                layoutClassroom.setError(null);
            } else {
                valid = false;
                layoutClassroom.setError("name is to short!");
            }
        }

        return valid;

    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
