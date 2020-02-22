package com.example.degitalclassroom.teacher.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.teacher.activity.UploadDocumentsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeaDocumentsFragment extends Fragment {

    private Toolbar toolbar;
    private RecyclerView nRecyclerView;
    private TextView nUploadDocuments;
    private Context context;

    public TeaDocumentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tea_report, container, false);

        nRecyclerView = (RecyclerView) view.findViewById(R.id.documents_recycler);
        nUploadDocuments = (TextView) view.findViewById(R.id.add_upload_doc_txt);

        nUploadDocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Intent intent = new Intent(getContext(), UploadDocumentsActivity.class);
                activity.startActivity(intent);
            }
        });

        return view;
    }

}
