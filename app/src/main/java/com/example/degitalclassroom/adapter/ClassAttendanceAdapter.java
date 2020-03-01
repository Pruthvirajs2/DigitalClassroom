package com.example.degitalclassroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.degitalclassroom.R;
import com.example.degitalclassroom.interfaces.OnItemClickListener;
import com.example.degitalclassroom.model.Attendance;
import com.example.degitalclassroom.model.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClassAttendanceAdapter extends RecyclerView.Adapter<ClassAttendanceAdapter.ClassViewHolder> {

    public ArrayList<User> mStudents = new ArrayList<>();
    public Context mContext;

    public ClassAttendanceAdapter(ArrayList<User> mStudents, Context mContext) {
        this.mStudents = mStudents;
        this.mContext = mContext;
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

        final User user = mStudents.get(position);

        holder.mStudentName.setText(user.getFirst_name() + " " + user.getLast_name());
        holder.mClassName.setText(user.getClassName());

        holder.checkASwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                user.setCheckAttendance(isChecked);
            }
        });


        Glide.with(mContext)
                .load(R.drawable.avatar)
                .into(holder.profileIcon);


    }


    @Override
    public int getItemCount() {
        return mStudents.size();
    }


    public class ClassViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView profileIcon;
        public TextView mStudentName, mClassName;
        public Switch checkASwitch;
        public View view;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            profileIcon = (CircleImageView) view.findViewById(R.id.icon);
            mStudentName = (TextView) view.findViewById(R.id.text_name);
            mClassName = (TextView) view.findViewById(R.id.text_class);
            checkASwitch = (Switch) view.findViewById(R.id.item_marked);
        }
    }


}
