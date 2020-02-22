package com.example.degitalclassroom.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.teacher.TeacherMainActivity;

public class DetailsPdfActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView nDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_pdf);

        toolbar=(Toolbar)findViewById(R.id.tool_bar);
        toolbar.setTitle("Details");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        nDescription = (TextView)findViewById(R.id.des);
        nDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DetailsPdfActivity.this, TeacherMainActivity.class);
                startActivity(intent);
            }
        });
    }
}
