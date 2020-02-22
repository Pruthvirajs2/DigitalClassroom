package com.example.degitalclassroom.teacher.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.degitalclassroom.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeaProfileFragment extends Fragment {

   private TextView nName,nContact,nEmail;
    public TeaProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tea_notification, container, false);

        nName = (TextView) view.findViewById(R.id.fac_name);
        nContact = (TextView) view.findViewById(R.id.content_fac);
        nEmail = (TextView) view.findViewById(R.id.email_fac);

        return view;
    }

}
