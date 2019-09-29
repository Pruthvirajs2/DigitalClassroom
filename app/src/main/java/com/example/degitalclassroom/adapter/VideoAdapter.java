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

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.HolderView> {

    private Context context;
    private ArrayList<Faculty> videoArrayList = new ArrayList<>();

    public VideoAdapter(Context context, ArrayList<Faculty> videoArrayList) {
        this.context = context;
        this.videoArrayList = videoArrayList;
    }

    @NonNull
    @Override
    public VideoAdapter.HolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.videos_item_list, viewGroup, false);
        context = viewGroup.getContext();
        return new VideoAdapter.HolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.HolderView holder, int i) {

        holder.setIsRecyclable(false);

        final Faculty faculty = videoArrayList.get(i);
        holder.nName.setText(faculty.getName());
        holder.nProfileImage.setImageResource(faculty.getImage());

    }

    @Override
    public int getItemCount() {
        return videoArrayList.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {

        private View view;
        private TextView nName;
        private ImageView nProfileImage;

        public HolderView(@NonNull View itemView) {
            super(itemView);

            view = itemView;
            nName = (TextView) view.findViewById(R.id.name_tea);
            nProfileImage = (ImageView) view.findViewById(R.id.video_image);

        }
    }
}


