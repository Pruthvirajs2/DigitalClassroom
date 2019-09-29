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

public class PeopleProfileAdapter extends RecyclerView.Adapter<PeopleProfileAdapter.HolderView> {

    private Context context;
    private ArrayList<Faculty> peopleArrayList = new ArrayList<>();

    public PeopleProfileAdapter(Context context, ArrayList<Faculty> peopleArrayList) {
        this.context = context;
        this.peopleArrayList = peopleArrayList;
    }

    @NonNull
    @Override
    public PeopleProfileAdapter.HolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.people_item_list, viewGroup, false);
        context = viewGroup.getContext();
        return new HolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleProfileAdapter.HolderView holder, int i) {

        holder.setIsRecyclable(false);

        final Faculty faculty = peopleArrayList.get(i);
        holder.nName.setText(faculty.getName());
       // holder.nProfileImage.setImageResource(faculty.getImage());

    }

    @Override
    public int getItemCount() {
        return peopleArrayList.size();
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
