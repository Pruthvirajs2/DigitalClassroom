package com.example.degitalclassroom.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.model.Faculty;

import java.util.ArrayList;

public class FacultyHomeAdapter extends RecyclerView.Adapter<FacultyHomeAdapter.HolderView> {

    private Context context;
    private ArrayList<Faculty> facultyArrayList = new ArrayList<>();

    public FacultyHomeAdapter(Context context, ArrayList<Faculty> facultyArrayList) {
        this.context = context;
        this.facultyArrayList = facultyArrayList;
    }

    @NonNull
    @Override
    public FacultyHomeAdapter.HolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_item_list, viewGroup, false);
        context = viewGroup.getContext();
        return new HolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyHomeAdapter.HolderView holder, int i) {

        holder.setIsRecyclable(false);

        final Faculty faculty = facultyArrayList.get(i);
        holder.nName.setText(faculty.getName());

    }

    @Override
    public int getItemCount() {
        return facultyArrayList.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {

        private View view;
        private TextView nName,nSubject,nTime,nTitle;
        private ImageView nProfileImage;

        public HolderView(@NonNull View itemView) {
            super(itemView);

            view = itemView;
            nName = (TextView) view.findViewById(R.id.name);
            nSubject = (TextView) view.findViewById(R.id.subject);
            nTime = (TextView) view.findViewById(R.id.time);
            nTitle = (TextView) view.findViewById(R.id.description);
            nProfileImage = (ImageView) view.findViewById(R.id.profile_image);

        }
    }
}
