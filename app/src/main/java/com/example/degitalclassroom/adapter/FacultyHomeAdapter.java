package com.example.degitalclassroom.adapter;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.degitalclassroom.R;
import com.example.degitalclassroom.activity.PDFListActivity;
import com.example.degitalclassroom.model.Faculty;
import com.example.degitalclassroom.model.User;

import java.util.ArrayList;

public class FacultyHomeAdapter extends RecyclerView.Adapter<FacultyHomeAdapter.HolderView> {

    private Context context;
    private ArrayList<User> facultyArrayList = new ArrayList<>();

    public FacultyHomeAdapter(Context context, ArrayList<User> facultyArrayList) {
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

        final User user = facultyArrayList.get(i);
        holder.nName.setText(user.getUserid());
        holder.nSubject.setText(user.getEmail());
        holder.nTime.setText(user.getContact());
        holder.nTitle.setText(user.getClassName());

        Glide.with(context)
                .load(R.drawable.avatar)
                .centerCrop()
                .into(holder.nProfileImage);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PDFListActivity.class)
                        .putExtra("userid", user.getId()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return facultyArrayList.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {

        private View view;
        private TextView nName, nSubject, nTime, nTitle;
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
