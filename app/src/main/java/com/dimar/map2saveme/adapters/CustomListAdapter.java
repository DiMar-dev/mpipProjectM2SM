package com.dimar.map2saveme.adapters;

import android.content.Context;
import android.graphics.Region;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.dimar.map2saveme.R;
import com.dimar.map2saveme.clickListener.RecyclerViewClickListener;
import com.dimar.map2saveme.holders.CustomListViewHolder;
import com.dimar.map2saveme.models.Photo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomListAdapter extends RecyclerView.Adapter implements Filterable {

    List<Photo> dataset;
    List<Photo> filteredDataset;

    private Context context;
    private static RecyclerViewClickListener itemListener;


    public CustomListAdapter(Context context, RecyclerViewClickListener clickListener) {
        this.dataset = new ArrayList<>();
        this.filteredDataset = new ArrayList<>();

        this.context = context;
        this.itemListener = clickListener;
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
//        sb.append(" ");
//        sb.append(dataset.get(position).getPhotographerID());
        ((CustomListViewHolder)holder).setText(sb.toString(),
                                                dataset.get(position).getImageBase64(),
                                                dataset.get(position).getDate());
    }

    @Override
    synchronized public int getItemCount() {
        return dataset.size();
    }

    //Firebase RealTime Database OFFLINE???
    synchronized public void updateDataset(Photo newDataset) {

        Photo result=dataset.stream().filter(photo -> photo.getImageID().equals(newDataset.getImageID()))
                .findFirst().orElse(null);
        if(result==null){
            dataset.add(newDataset);
        }else{
            if(newDataset.getImageBase64().equals("DELETED")){
                dataset.remove(dataset.indexOf(result));
            }else {
                dataset.set(dataset.indexOf(result),newDataset);
            }
        }
        filteredDataset.clear();
        filteredDataset.addAll(dataset);
        notifyDataSetChanged();
    }



    public static RecyclerViewClickListener getItemListener() {
        return itemListener;
    }

    public List<Photo> getDataset() {
        return dataset;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Photo> filterList=new ArrayList<>();

                if(charSequence==null || charSequence.length()==0){
                    filterList.addAll(filteredDataset);
                }else {
                    String query = charSequence.toString().toLowerCase().trim();

                    filteredDataset.stream().forEach(photo -> {
                        if (photo.getAndimalID().contains(query))
                            filterList.add(photo);
                    });
                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=filterList;
                return filterResults;
            }

            @Override
            synchronized protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataset.clear();
                dataset.addAll((List)filterResults.values);
                notifyDataSetChanged();
            }
        };
    }
}
