package com.dimar.map2saveme.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.dimar.map2saveme.R;

public class CustomListViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView textView;

    public CustomListViewHolder(View view) {
        super(view);

        imageView=view.findViewById(R.id.imageViewFind);
        textView=view.findViewById(R.id.textViewFind);
    }
    public void setText(String text) {
        textView.setText(text);
//        imageView.setImageResource(R.drawable.dpng);
//        itemView.setOnClickListener(listener);
    }
}
