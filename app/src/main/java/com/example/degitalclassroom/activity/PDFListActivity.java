package com.example.degitalclassroom.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.adapter.PdfListAdapter;
import com.example.degitalclassroom.model.Faculty;

import java.util.ArrayList;

public class PDFListActivity extends AppCompatActivity {

    private RecyclerView nRecyclerView;
    private PdfListAdapter pdfListAdapter;
    private ArrayList<Faculty> pdfArrayList = new ArrayList<>();
    private ImageView nBackImage;
    private TextView nFacultyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdflist);

        nFacultyName = (TextView)findViewById(R.id.faculty_name_txt);

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
