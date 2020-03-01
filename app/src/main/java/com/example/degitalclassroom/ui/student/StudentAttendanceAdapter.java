package com.example.degitalclassroom.ui.student;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.model.Attendance;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class StudentAttendanceAdapter extends RecyclerView.Adapter<StudentAttendanceAdapter.StudentViewHolder> {

    ArrayList<Attendance> mAttendances = new ArrayList<>();
    Context mContext;

    public StudentAttendanceAdapter(ArrayList<Attendance> mAttendances, Context mContext) {
        this.mAttendances = mAttendances;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_sheet_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {


        Attendance attendance = mAttendances.get(position);

        holder.txtTimestamp.setText(attendance.getDate());


        if (attendance.isCheckAttendance()) {
            holder.txtAttendance.setText("P");
        } else {
            holder.txtAttendance.setText("A");
        }

    }

    @Override
    public int getItemCount() {
        return mAttendances.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView txtTimestamp, txtAttendance;
        View view;


        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            txtTimestamp = (TextView) view.findViewById(R.id.date);
            txtAttendance = (TextView) view.findViewById(R.id.attendance);
        }
    }
}
