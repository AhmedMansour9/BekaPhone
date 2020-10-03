package com.bekya.bekyaa.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import com.bekya.bekyaa.ChangeLanguage;
import com.bekya.bekyaa.Model.Gallery;
import com.bekya.bekyaa.Interface.Open_Galler_View;
import com.bekya.bekyaa.R;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class Slider_Adapter extends RecyclerView.Adapter<Slider_Adapter.MyViewHolder>{

    private List<Gallery> filteredList=new ArrayList<>();
    SharedPreferences.Editor share;

    public static String TotalPrice;
    View itemView;
    Context con;
    String prrice;
    Open_Galler_View delete_galler_view;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,location,T_Model,T_Price,count;
        ImageView mobile;
        ProgressBar progressBar;
        ImageView btncart;
        public ImageView plus,minus,delete;
        ImageView imggg;
        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            imggg=view.findViewById(R.id.viewPagerItem_image1);
            location=view.findViewById(R.id.location);
        }


    }

    public Slider_Adapter(List<Gallery> list, Context context){
        this.filteredList=list;
        this.con=context;
    }
    public void DeleteImage(Open_Galler_View delete_galler_view){
        this.delete_galler_view=delete_galler_view;

    }

    @Override
    public Slider_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.banner, parent, false);
        return new Slider_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Slider_Adapter.MyViewHolder holder, final int position) {


        String i = filteredList.get(position).getImg();
        Uri u = Uri.parse(i);
//        holder.progressBar.setVisibility(View.VISIBLE);
         holder.title.setText(filteredList.get(position).getTitle());
        if(ChangeLanguage.getLanguage(con).equals("en")){
            holder.location.setText(filteredList.get(position).getCit_en());
        }   else {
            holder.location.setText(filteredList.get(position).getCit_ar());

        }

        Glide.with(con)
                .load(u)
                .apply(new RequestOptions())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            holder.ProgrossSpare.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }

                })
                .into(holder.imggg);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_galler_view.delete(filteredList.get(position).getLink());
            }
        });

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
