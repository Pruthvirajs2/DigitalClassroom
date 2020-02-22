package com.example.degitalclassroom.ui;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.helper.SessionManager;
import com.example.degitalclassroom.model.Student;
import com.example.degitalclassroom.model.User;
import com.example.degitalclassroom.teacher.TeacherMainActivity;
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

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {


    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private SpinKitView progressBar;
    private Button btnSignup, btnLogin, btnReset;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        // Session manager
        session = new SessionManager(getApplicationContext());

       /* if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, StudentActivity.class));
            finish();
        }*/

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity

            if (session.getAuthorization().equals("Teacher")) {

                Intent intent = new Intent(LoginActivity.this, TeacherMainActivity.class);
                startActivity(intent);
                this.finish();

            } else if (session.getAuthorization().equals("Student")) {
                Intent intent = new Intent(LoginActivity.this, StudentActivity.class);
                startActivity(intent);
                this.finish();
            }

        }

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (SpinKitView) findViewById(R.id.spin_kit);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                btnLogin.setEnabled(false);
                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.

                                if (!task.isSuccessful()) {
                                    // there was an error
                                    btnLogin.setEnabled(false);
                                    progressBar.setVisibility(View.GONE);
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        btnLogin.setEnabled(true);
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {

                                    mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            if (dataSnapshot.exists()) {

                                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                    //getting User from firebase console
                                                    User user = postSnapshot.getValue(User.class);
                                                    if (user != null) {
                                                        if (user.getId().contentEquals(Objects.requireNonNull(auth.getUid()))) {

                                                            if (user.getDesignation().equals("Teacher")) {
                                                                session.setLogin(true);
                                                                session.setAuthorization("Teacher");
                                                                Intent intent = new Intent(LoginActivity.this, TeacherMainActivity.class);
                                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                startActivity(intent);
                                                                finish();
                                                            } else if (user.getDesignation().equals("Student")) {
                                                                session.setLogin(true);
                                                                session.setAuthorization("Student");
                                                                Intent intent = new Intent(LoginActivity.this, StudentActivity.class);
                                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                startActivity(intent);
                                                                finish();
                                                            } else {
                                                                btnLogin.setEnabled(true);
                                                                progressBar.setVisibility(View.GONE);
                                                                Toast.makeText(LoginActivity.this, "Failed Login. Please Try Again", Toast.LENGTH_SHORT).show();
                                                            }

                                                        }
                                                    }

                                                }

                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            btnLogin.setEnabled(true);
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(LoginActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });


                                }
                            }
                        });
            }
        });
    }
}
