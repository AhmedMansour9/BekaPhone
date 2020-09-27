package com.starbugs_Bekaphone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.starbugs_Bekaphone.ChangeLanguage;
import com.starbugs_Bekaphone.Interface.CityId_View;
import com.starbugs_Bekaphone.Model.SubCategories_Model;
import com.starbugs_Bekaphone.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class SubCategories_Adapter extends RecyclerView.Adapter<SubCategories_Adapter.MyViewHolder> {
    CityId_View cityId_view;
    List<SubCategories_Model> array=new ArrayList<>();
    Context context;
    int row_index=0;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Title;
        View views;
        RelativeLayout relative_row;

        public MyViewHolder(View view) {
            super(view);
            Title=view.findViewById(R.id.Title);
            views=view.findViewById(R.id.view);
            relative_row=view.findViewById(R.id.relative_row);

        }

    }
    public SubCategories_Adapter(List<SubCategories_Model> moviesList , Context context) {
        this.array = moviesList;
        this.context=context;

    }
    public void setClickListener(CityId_View itemClickListener) {
        this.cityId_view = itemClickListener;
    }


    @Override
    public SubCategories_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_subcategories, parent, false);
        return new SubCategories_Adapter.MyViewHolder(itemView);
    }




    @Override
    public void onBindViewHolder(final SubCategories_Adapter.MyViewHolder holder, final int position) {
        if(ChangeLanguage.getLanguage(context).equals("en")) {
            holder.Title.setText(array.get(position).getCat_en());
        }else {
            holder.Title.setText(array.get(position).getCat_ar());

        }

        if(row_index==position){
            cityId_view.Id(array.get(position).getSub_key(),array.get(position).getCat_en(),"");
            holder.views.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.views.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index=position;
                notifyDataSetChanged();
                    cityId_view.Id(array.get(position).getSub_key(),array.get(position).getCat_ar(),"");
            }
        });

    }




    @Override
    public int getItemCount() {
        return   array.size();

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
