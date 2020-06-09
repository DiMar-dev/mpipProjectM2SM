package com.dimar.map2saveme.holders;

import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.dimar.map2saveme.R;
import com.dimar.map2saveme.adapters.CustomListAdapter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class CustomListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView imageView;
    TextView textView;

    public CustomListViewHolder(View view) {
        super(view);

        imageView=view.findViewById(R.id.imageViewFind);
        textView=view.findViewById(R.id.textViewFind);

        view.setOnClickListener(this);
    }
    public void setText(String text,String base64string,long date) {

        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(date),ZoneId.systemDefault());

//        String base64Image = base64string.split(",")[1];
        String base64Image=base64string;
        byte[] data = Base64.decode(base64Image.getBytes(), Base64.DEFAULT);

        String textToShow=text+" "+localDateTime.toString();

        imageView.setImageBitmap(BitmapFactory.decodeByteArray(data,0,data.length));
        textView.setText(textToShow);
//        itemView.setOnClickListener(listener);
    }

    @Override
    public void onClick(View view) {
        CustomListAdapter.getItemListener().recyclerViewListClicked(view,getLayoutPosition());
    }
}
