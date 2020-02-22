package com.example.degitalclassroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.degitalclassroom.R;
import com.example.degitalclassroom.interfaces.OnItemClickListener;
import com.example.degitalclassroom.model.Student;
import com.example.degitalclassroom.model.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClassAttendanceAdapter extends RecyclerView.Adapter<ClassAttendanceAdapter.ClassViewHolder> {

    private ArrayList<User> mStudents = new ArrayList<>();
    private Context mContext;
    public OnItemClickListener mListener;

    public ClassAttendanceAdapter(ArrayList<User> mStudents, Context mContext, OnItemClickListener mListener) {
        this.mStudents = mStudents;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_item_list, parent, false);
        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, final int position) {
        holder.setIsRecyclable(false);

        User user = mStudents.get(position);

        holder.mStudentName.setText(user.getFirst_name() + " " + user.getLast_name());
        holder.mClassName.setText(user.getClassName());

        Glide.with(mContext)
                .load(R.drawable.avatar)
                .into(holder.profileIcon);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStudents.size();
    }

    public class ClassViewHolder extends RecyclerView.ViewHolder {

        CircleImageView profileIcon;
        TextView mStudentName, mClassName;
        View view;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            profileIcon = (CircleImageView) view.findViewById(R.id.icon);
            mStudentName = (TextView) view.findViewById(R.id.text_name);
            mClassName = (TextView) view.findViewById(R.id.text_class);
        }
    }
}
