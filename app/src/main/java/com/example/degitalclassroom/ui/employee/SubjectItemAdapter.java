package com.example.degitalclassroom.ui.employee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.interfaces.OnItemClickListener;
import com.example.degitalclassroom.model.Subject;

import java.util.ArrayList;

public class SubjectItemAdapter extends RecyclerView.Adapter<SubjectItemAdapter.SubjectViewHolder> {

    ArrayList<Subject> mSubjects = new ArrayList<>();
    Context mContext;
     OnItemClickListener mListener;


    public SubjectItemAdapter(ArrayList<Subject> mSubjects, Context mContext, OnItemClickListener mListener) {
        this.mSubjects = mSubjects;
        this.mContext = mContext;
        this.mListener = mListener;
    }


    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_item_list, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, final int position) {

        Subject subject = mSubjects.get(position);

        holder.txtSubName.setText(subject.getSubName());
        holder.txtClassName.setText(subject.getClassName());
        holder.txtAcademic.setText(subject.getSemester() + " | " + subject.getBatchYear());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSubjects.size();
    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder {


        TextView txtSubName, txtClassName, txtAcademic;
        View view;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            txtSubName = (TextView) view.findViewById(R.id.suject_name);
            txtClassName = (TextView) view.findViewById(R.id.class_name);
            txtAcademic = (TextView) view.findViewById(R.id.academic);

        }
    }
}
