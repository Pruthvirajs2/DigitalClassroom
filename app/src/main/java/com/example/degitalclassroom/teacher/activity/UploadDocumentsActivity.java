package com.example.degitalclassroom.teacher.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.degitalclassroom.R;

public class UploadDocumentsActivity extends AppCompatActivity {

    private TextView nSelectDocument;
    private EditText nDesEdit;
    private Button nSaveButton;
    private ImageView nImageView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_documents);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Upload Documents");

        nSelectDocument = (TextView)findViewById(R.id.add_docu_txt);
        nDesEdit = (EditText)findViewById(R.id.dec_edit);
        nSaveButton = (Button)findViewById(R.id.send_doc_btn);
        nImageView = (ImageView)findViewById(R.id.image_upload);
    }
}
