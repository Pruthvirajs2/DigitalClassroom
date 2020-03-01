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

import com.example.degitalclassroom.R;
import com.example.degitalclassroom.activity.DetailsPdfActivity;
import com.example.degitalclassroom.model.Faculty;

import java.util.ArrayList;

public class PdfListAdapter extends RecyclerView.Adapter<PdfListAdapter.HolderView> {

    private Context context;
    private ArrayList<Faculty> pdfArrayList = new ArrayList<>();

    public PdfListAdapter(Context context, ArrayList<Faculty> pdfArrayList) {
        this.context = context;
        this.pdfArrayList = pdfArrayList;
    }

    @NonNull
    @Override
    public PdfListAdapter.HolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.document_item_list, viewGroup, false);
        context = viewGroup.getContext();
        return new HolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfListAdapter.HolderView holder, int i) {

        holder.setIsRecyclable(false);


    }

    @Override
    public int getItemCount() {
        return pdfArrayList.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {

        private View view;

        public HolderView(@NonNull View itemView) {
            super(itemView);
            view = itemView;

        }
    }
}

