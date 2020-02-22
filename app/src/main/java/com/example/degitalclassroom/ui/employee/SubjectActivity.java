package com.example.degitalclassroom.ui.employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.custom.DividerItemDecoration;
import com.example.degitalclassroom.helper.SessionManager;
import com.example.degitalclassroom.interfaces.OnItemClickListener;
import com.example.degitalclassroom.model.Subject;
import com.example.degitalclassroom.teacher.activity.AddStudentActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SubjectActivity extends AppCompatActivity {


    ImageView addSubjectView, previousView;
    RecyclerView mSubjectRecyclerView;

    private FirebaseAuth auth;
    private DatabaseReference subjectReference, classReference;
    private FirebaseDatabase mFirebaseInstance;
    private SessionManager session;


    SubjectItemAdapter subjectItemAdapter;
    ArrayList<Subject> mSubjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        auth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference
        subjectReference = mFirebaseInstance.getReference("Subject");
        classReference = mFirebaseInstance.getReference("Classroom");


        initViews();


        addSubjectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(SubjectActivity.this);
            }
        });

        mSubjectRecyclerView.setHasFixedSize(true);
        mSubjectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSubjectRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        subjectReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                mSubjects.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        Subject subject = dataSnapshot.getValue(Subject.class);
                        mSubjects.add(0, subject);
                    }

                    subjectItemAdapter = new SubjectItemAdapter(mSubjects, SubjectActivity.this, new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            Toast.makeText(SubjectActivity.this, mSubjects.get(position).getSubName(), Toast.LENGTH_SHORT).show();

                        }
                    });

                    mSubjectRecyclerView.setAdapter(subjectItemAdapter);
                    subjectItemAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(SubjectActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        previousView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void initViews() {

        addSubjectView = (ImageView) findViewById(R.id.add_subject);
        previousView = (ImageView) findViewById(R.id.back_image);
        mSubjectRecyclerView = (RecyclerView) findViewById(R.id.subject_list);

    }


    public void showDialog(final Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_subject_dialog);


        final Spinner mClassSpinner = (Spinner) dialog.findViewById(R.id.subjectClassName);
        final Spinner mSemesterSpinner = (Spinner) dialog.findViewById(R.id.semester_name);
        final Spinner mBatchSpinner = (Spinner) dialog.findViewById(R.id.batch_name);


        classReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> classrooms = new ArrayList<String>();

                if (dataSnapshot.exists()) {
                    for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                        String areaName = areaSnapshot.child("classroom").getValue(String.class);
                        classrooms.add(areaName);
                    }

                    //Creating the ArrayAdapter instance having the classrooms list
                    ArrayAdapter aa = new ArrayAdapter(context, android.R.layout.simple_spinner_item, classrooms);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    mClassSpinner.setAdapter(aa);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final TextInputEditText subjectText = (TextInputEditText) dialog.findViewById(R.id.input_subjectName);


        ImageView closeDialog = (ImageView) dialog.findViewById(R.id.close_dialog);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button dialogButton = (Button) dialog.findViewById(R.id.createSubject);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String subjectName = subjectText.getText().toString().trim();
                String semester = mSemesterSpinner.getSelectedItem().toString().trim();
                String className = mClassSpinner.getSelectedItem().toString().trim();
                String batch = mBatchSpinner.getSelectedItem().toString().trim();

                if (TextUtils.isEmpty(subjectName)) {
                    Toast.makeText(context, "Subject Required Filed.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (semester.equals("Default")) {
                    Toast.makeText(context, "Semester Required.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (batch.equals("Default")) {
                    Toast.makeText(context, "Batch Required.", Toast.LENGTH_SHORT).show();
                    return;
                }


                //getting a unique id using push().getKey() method
                //it will create a unique id and we will use it as the Primary Key for our Subject
                String id = subjectReference.push().getKey();

                //creating an Artist Object
                Subject subject = new Subject(id, subjectName, className, batch, semester);

                //Saving the Artist
                subjectReference.child(id).setValue(subject).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                        }
                    }
                });


            }
        });


        dialog.show();

    }

}
