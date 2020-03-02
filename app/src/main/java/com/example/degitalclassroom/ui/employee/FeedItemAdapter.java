package com.example.degitalclassroom.ui.employee;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.degitalclassroom.R;
import com.example.degitalclassroom.model.Feeds;

import java.io.File;
import java.util.ArrayList;

public class FeedItemAdapter extends RecyclerView.Adapter<FeedItemAdapter.FeedViewHolder> {

    ArrayList<Feeds> mFeeds = new ArrayList<>();
    Context mContext;

    public FeedItemAdapter(ArrayList<Feeds> mFeeds, Context mContext) {
        this.mFeeds = mFeeds;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.document_item_list, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        final Feeds feeds = mFeeds.get(position);

        holder.txtTitleName.setText(feeds.getContent());
        holder.txtDescription.setText(feeds.getSubName());
        holder.txtTimestamp.setText(feeds.getTimestamp());

        if (feeds.getType().equals("0")) {
            holder.thumbnail.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(feeds.getThumbnail())
                   // .centerCrop()
                    .placeholder(R.drawable.unavailable)
                    .into(holder.thumbnail);

        } else {
            holder.thumbnail.setVisibility(View.GONE);
        }

        if (feeds.getType().equals("1")) {
            holder.documentLayout.setVisibility(View.VISIBLE);
            holder.documentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        mContext.startActivity(new Intent(mContext, DocumentViewerActivity.class)
                                .putExtra("link", feeds.getThumbnail()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } else {
            holder.documentLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mFeeds.size();
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitleName, txtDescription, txtTimestamp;
        LinearLayout documentLayout;
        View view;
        ImageView thumbnail;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            txtTitleName = (TextView) view.findViewById(R.id.titleName);
            txtDescription = (TextView) view.findViewById(R.id.description);
            txtTimestamp = (TextView) view.findViewById(R.id.timestamp);
            documentLayout = (LinearLayout) view.findViewById(R.id.document_layout);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

        }
    }
}
