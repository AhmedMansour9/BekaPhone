package bekya.bekyaa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bekya.bekyaa.ChangeLanguage;
import bekya.bekyaa.Interface.CityId_View;
import bekya.bekyaa.Model.Cities_Response;
import bekya.bekyaa.R;

import androidx.recyclerview.widget.RecyclerView;

public class Cities_Adapter extends RecyclerView.Adapter<Cities_Adapter.MyViewHolder> {
    CityId_View cityId_view;
    List<Cities_Response> array=new ArrayList<>();
    Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Title;
        public MyViewHolder(View view) {
            super(view);
            Title=view.findViewById(R.id.Title);

        }

    }
    public Cities_Adapter( List<Cities_Response> moviesList , Context context) {
        this.array = moviesList;
        this.context=context;

    }
    public void setClickListener(CityId_View itemClickListener) {
        this.cityId_view = itemClickListener;
    }


    @Override
    public Cities_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_cities, parent, false);
        return new Cities_Adapter.MyViewHolder(itemView);
    }




    @Override
    public void onBindViewHolder(final Cities_Adapter.MyViewHolder holder, final int position) {
     if(ChangeLanguage.getLanguage(context).equals("en")) {
         holder.Title.setText(array.get(position).getName());
     }else {
         holder.Title.setText(array.get(position).getName_ar());

     }

    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            cityId_view.Id(array.get(position).getName(),array.get(position).getId(),array.get(position).getName_ar());

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



