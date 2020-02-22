package com.example.degitalclassroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.model.Classroom;

import java.util.ArrayList;
import java.util.List;

public class ClassroomAdapter extends RecyclerView.Adapter<ClassroomAdapter.ViewHolder> {

    private List<Classroom> mClassrooms = new ArrayList<>();
    private Context mContext;

    public ClassroomAdapter(List<Classroom> mClassrooms, Context mContext) {
        this.mClassrooms = mClassrooms;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_classroom, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setIsRecyclable(false);

        Classroom classroom = mClassrooms.get(position);
         holder.mClassName.setText(classroom.getClassroom());

    }

    @Override
    public int getItemCount() {
        return mClassrooms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mClassName;
        private View mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            mClassName = (TextView) mView.findViewById(R.id.class_name);
        }
    }
}
