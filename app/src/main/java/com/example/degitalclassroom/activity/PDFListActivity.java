package com.example.degitalclassroom.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.degitalclassroom.R;

public class PDFListActivity extends AppCompatActivity {

    private RecyclerView nRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdflist);

        nRecyclerView = (RecyclerView)findViewById(R.id.pdf_recycler);
    }
}
