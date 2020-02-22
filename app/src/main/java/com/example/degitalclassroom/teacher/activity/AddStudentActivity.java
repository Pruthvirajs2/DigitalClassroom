package com.example.degitalclassroom.teacher.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.degitalclassroom.R;
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

public class AddStudentActivity extends AppCompatActivity {

    private TextInputEditText inputName, inputPassword, inputMobile, inputClass, inputEmail;
    private TextInputLayout layoutName, layoutPassword, layoutMobile, layoutEmail;
    private Button buttonSubmit;
    private AppCompatSpinner classCompatSpinner;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("Classroom");

        initViews();
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {

                    Toast.makeText(AddStudentActivity.this, "Wow", Toast.LENGTH_SHORT).show();
                }
            }
        });


        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    final List<String> classrooms = new ArrayList<String>();

                    for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                        String areaName = areaSnapshot.child("classroom").getValue(String.class);
                        classrooms.add(areaName);
                    }
                    ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(AddStudentActivity.this, android.R.layout.simple_spinner_item, classrooms);
                    areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    classCompatSpinner.setAdapter(areasAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    //this method is used to connect XML views to its Objects
    private void initViews() {
        inputEmail = (TextInputEditText) findViewById(R.id.editTextEmail);
        inputMobile = (TextInputEditText) findViewById(R.id.editTextMobile);
        inputPassword = (TextInputEditText) findViewById(R.id.editTextPassword);
        inputName = (TextInputEditText) findViewById(R.id.editTextName);
        classCompatSpinner = (AppCompatSpinner) findViewById(R.id.class_spinner);

        layoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        layoutMobile = (TextInputLayout) findViewById(R.id.textInputLayoutMobile);
        layoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        layoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        buttonSubmit = (Button) findViewById(R.id.save_data);


    }

    //This method is used to validate input given by user
    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String UserName = inputName.getText().toString();
        String mobile = inputMobile.getText().toString();
        String Email = inputEmail.getText().toString();
        String Password = inputPassword.getText().toString();

        //Handling validation for UserName field
        if (UserName.isEmpty()) {
            valid = false;
            layoutName.setError("Please enter valid name!");
        } else {
            if (UserName.length() > 5) {
                valid = true;
                layoutName.setError(null);
            } else {
                valid = false;
                layoutName.setError("name is to short!");
            }
        }


        //Handling validation for Mobile field
        if (mobile.isEmpty()) {
            valid = false;
            layoutMobile.setError("Please enter Mobile no!");
        } else {
            if (Password.length() > 5) {
                valid = true;
                layoutMobile.setError(null);
            } else {
                valid = false;
                layoutMobile.setError("Mobile no is to short!");
            }
        }

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            layoutEmail.setError("Please enter valid email!");
        } else {
            valid = true;
            layoutEmail.setError(null);
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            layoutPassword.setError("Please enter valid password!");
        } else {
            if (Password.length() > 5) {
                valid = true;
                layoutPassword.setError(null);
            } else {
                valid = false;
                layoutPassword.setError("Password is to short!");
            }
        }

        return valid;
    }
}
