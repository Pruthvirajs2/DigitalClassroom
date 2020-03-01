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
import com.example.degitalclassroom.model.Dashboard;

import java.util.ArrayList;

public class DashboardItemAdapter extends RecyclerView.Adapter<DashboardItemAdapter.DashboardViewHolder> {


    public ArrayList<Dashboard> mDashboards = new ArrayList<>();
    public Context mContext;
    public OnItemClickListener onItemClickListener;


    public DashboardItemAdapter(ArrayList<Dashboard> mDashboards, Context mContext, OnItemClickListener onItemClickListener) {
        this.mDashboards = mDashboards;
        this.mContext = mContext;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_item_list, parent, false);
        return new DashboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardViewHolder holder, final int position) {


        Dashboard dashboard = mDashboards.get(position);

        holder.txtTitle.setText(dashboard.getName());

        Glide.with(mContext)
                .load(dashboard.getIcon())
                .into(holder.icon);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDashboards.size();

    }

    public class DashboardViewHolder extends RecyclerView.ViewHolder {


        TextView txtTitle;
        ImageView icon;
        View view;

        public DashboardViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            icon = (ImageView) view.findViewById(R.id.icon_dash);
            txtTitle = (TextView) view.findViewById(R.id.title_name);

        }
    }
}
