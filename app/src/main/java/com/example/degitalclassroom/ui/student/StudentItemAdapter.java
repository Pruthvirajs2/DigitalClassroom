package com.example.degitalclassroom.ui.student;

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
import com.example.degitalclassroom.model.User;

import java.util.ArrayList;

public class StudentItemAdapter extends RecyclerView.Adapter<StudentItemAdapter.StdudentViewHolder> {

    ArrayList<User> mUsers = new ArrayList<>();
    Context mContext;

    public StudentItemAdapter(ArrayList<User> mUsers, Context mContext) {
        this.mUsers = mUsers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public StdudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_lists, parent, false);
        return new StdudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StdudentViewHolder holder, int position) {

        User user = mUsers.get(position);

        holder.txtStudentName.setText(user.getFirst_name());
        holder.txtClassName.setText(user.getClassName());
        holder.txtContactInfo.setText(user.getEmail());

        Glide.with(mContext)
                .load(R.drawable.avatar)
                .centerCrop()
                .into(holder.profileIcon);

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class StdudentViewHolder extends RecyclerView.ViewHolder {

        ImageView profileIcon;
        TextView txtStudentName, txtClassName, txtContactInfo;
        View view;

        public StdudentViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;

            profileIcon = (ImageView) view.findViewById(R.id.icon);
            txtStudentName = (TextView) view.findViewById(R.id.student_name);
            txtClassName = (TextView) view.findViewById(R.id.class_name);
            txtContactInfo = (TextView) view.findViewById(R.id.contact_info);
        }
    }
}
