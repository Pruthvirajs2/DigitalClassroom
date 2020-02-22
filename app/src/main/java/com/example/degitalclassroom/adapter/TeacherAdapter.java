package com.example.degitalclassroom.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.model.Faculty;

import java.util.ArrayList;

public class TeacherAdapter  extends RecyclerView.Adapter<TeacherAdapter.HolderView> {

    private Context context;
    private ArrayList<Faculty> teacherArrayList = new ArrayList<>();

    public TeacherAdapter(Context context, ArrayList<Faculty> teacherArrayList) {
        this.context = context;
        this.teacherArrayList = teacherArrayList;
    }

    @NonNull
    @Override
    public TeacherAdapter.HolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.people_item_list, viewGroup, false);
        context = viewGroup.getContext();
        return new TeacherAdapter.HolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherAdapter.HolderView holder, int i) {

        holder.setIsRecyclable(false);

        final Faculty faculty = teacherArrayList.get(i);
        holder.nName.setText(faculty.getName());
         holder.nProfileImage.setImageResource(faculty.getImage());

    }

    @Override
    public int getItemCount() {
        return teacherArrayList.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {

        private View view;
        private TextView nName;
        private ImageView nProfileImage;

        public HolderView(@NonNull View itemView) {
            super(itemView);

            view = itemView;
            nName = (TextView) view.findViewById(R.id.person_name);
            nProfileImage = (ImageView) view.findViewById(R.id.profile_icon);

        }
    }
}

