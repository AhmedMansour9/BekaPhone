package com.starbugs_Bekaphone.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.starbugs_Bekaphone.Interface.ItemClickListener;
import com.starbugs_Bekaphone.R;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by kunda on 10/2/2017.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView product_name;
    public ImageView product_image;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ProductViewHolder(View itemView) {
        super(itemView);

        product_name = (TextView)itemView.findViewById(R.id.product_name);
        product_image = (ImageView)itemView.findViewById(R.id.product_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
