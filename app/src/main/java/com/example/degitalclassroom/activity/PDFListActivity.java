package com.example.degitalclassroom.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.adapter.PdfListAdapter;
import com.example.degitalclassroom.model.Faculty;

import java.util.ArrayList;

public class PDFListActivity extends AppCompatActivity {

    private RecyclerView nRecyclerView;
    private PdfListAdapter pdfListAdapter;
    private ArrayList<Faculty> pdfArrayList = new ArrayList<>();
    private ImageView nBackImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdflist);

        nRecyclerView = (RecyclerView)findViewById(R.id.pdf_recycler);
        nRecyclerView.setHasFixedSize(true);
        nRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        pdfListAdapter = new PdfListAdapter(this, getPdfList());
        nRecyclerView.setAdapter(pdfListAdapter);

        nBackImage = (ImageView)findViewById(R.id.back_image);
        nBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });
    }

    private ArrayList<Faculty> getPdfList() {

        pdfArrayList.clear();

        Faculty faculty = new Faculty(
                "1",
                "Rahul sharma"
        );pdfArrayList.add(faculty);

        faculty = new Faculty(
                "2",
                "Neha Patel"
        );pdfArrayList.add(faculty);

        faculty = new Faculty(
                "3",
                "Ritu Roy"
        );pdfArrayList.add(faculty);

        return  pdfArrayList;
    }
}
