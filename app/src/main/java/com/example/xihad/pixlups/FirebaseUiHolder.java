package com.example.xihad.pixlups;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FirebaseUiHolder extends RecyclerView.ViewHolder {

    View mView;

    public FirebaseUiHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

    }

    public void setCaption(String caption) {
        TextView captionView = mView.findViewById(R.id.itemnameId);

        captionView.setText(caption);
    }

    public void setPrice(String price) {
        TextView priceView = mView.findViewById(R.id.itempriceId);

        priceView.setText(price);
    }

    public void setImageuri(String imageuri) {

        ImageView imageView = mView.findViewById(R.id.itemimgId);
        Picasso.get()
                .load(imageuri)
                .placeholder(R.drawable.loading)
                .into(imageView);
    }



}
