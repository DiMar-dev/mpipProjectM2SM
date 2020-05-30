package com.dimar.map2saveme.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.dimar.map2saveme.R;
import com.dimar.map2saveme.holders.CustomListViewHolder;
import com.dimar.map2saveme.models.Photo;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends RecyclerView.Adapter {
    List<Photo> dataset;

    public CustomListAdapter() {
        this.dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent, false);

        return new CustomListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StringBuilder sb=new StringBuilder();
        sb.append(dataset.get(position).getAndimalID());
        sb.append(" ");
        sb.append(dataset.get(position).getPhotographerID());
        sb.append(" ");
        sb.append(dataset.get(position).getDate().toString());
        ((CustomListViewHolder)holder).setText(sb.toString());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void updateDataset(List<Photo> newDataset) {
        dataset.addAll(newDataset);
        notifyDataSetChanged();
    }
}
