package com.example.degitalclassroom.ui;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.model.User;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText inputEmail, inputPassword, inputFirstName, inputLastName, inputMobile;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private SpinKitView progressBar;
    private FirebaseAuth auth;

    private RelativeLayout classLayout;
    private AppCompatSpinner deskSpinner, classRoomSpinner;
    private DatabaseReference mFirebaseDatabase, classReference;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;
    private String selectedClass;


    String[] register = {"Default", "Teacher", "Student"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        classReference = mFirebaseInstance.getReference("Classroom");

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputFirstName = (EditText) findViewById(R.id.first_name);
        inputLastName = (EditText) findViewById(R.id.last_name);
        inputMobile = (EditText) findViewById(R.id.mobile);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (SpinKitView) findViewById(R.id.spin_kit);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        deskSpinner = (AppCompatSpinner) findViewById(R.id.designation_spinner);
        classRoomSpinner = (AppCompatSpinner) findViewById(R.id.class_spinner);
        classLayout = (RelativeLayout) findViewById(R.id.class_Layout);


        deskSpinner.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the register list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, register);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        deskSpinner.setAdapter(aa);


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
                    ArrayAdapter aa = new ArrayAdapter(RegisterActivity.this, android.R.layout.simple_spinner_item, classrooms);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    classRoomSpinner.setAdapter(aa);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                final String firstName = inputFirstName.getText().toString().trim();
                final String lastName = inputLastName.getText().toString().trim();
                final String mobileNumber = inputMobile.getText().toString().trim();
                final String desk = deskSpinner.getSelectedItem().toString().trim();
                final String className = classRoomSpinner.getSelectedItem().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (desk.equals("Default")) {
                    Toast.makeText(getApplicationContext(), "Please choose designation!", Toast.LENGTH_SHORT).show();
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                userId = mFirebaseDatabase.push().getKey();
                                User userMapping;
                                String username = firstName + lastName;

                                if (desk.equals("Teacher")) {

                                    userMapping = new User(
                                            auth.getUid(), username, firstName, lastName,
                                            "Default", desk, email, "dd/mm/yyyy", mobileNumber,
                                            "icon", className
                                    );
                                    mFirebaseDatabase.child(auth.getUid()).setValue(userMapping);

                                }
                                if (desk.equals("Student")) {

                                    userMapping = new User(
                                            auth.getUid(), username, firstName, lastName,
                                            "Default", desk, email, "dd/mm/yyyy", mobileNumber,
                                            "icon", className
                                    );
                                    mFirebaseDatabase.child(auth.getUid()).setValue(userMapping);

                                }

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    finish();

                                }
                            }
                        });

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                classLayout.setVisibility(View.GONE);
                break;
            case 1:
                classLayout.setVisibility(View.VISIBLE);
                break;
            case 2:
                classLayout.setVisibility(View.VISIBLE);
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
