package bekya.bekyaa.adapter;

/**
 * Created by HP on 31/03/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import bekya.bekyaa.Activites.ReportsPopup;
import bekya.bekyaa.Interface.itemViewinterface;
import bekya.bekyaa.R;
import bekya.bekyaa.Model.Retrivedata;
import bekya.bekyaa.tokenid.SharedPrefManager;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class Adapteritems extends RecyclerView.Adapter<Adapteritems.MyViewHolder>  implements Filterable{
    private List<Retrivedata> mArrayList;
    ArrayList<Integer> list=new ArrayList<>();
    itemViewinterface itemclick;
    public static List<Retrivedata> array=new ArrayList<>();
    public imgclick btnclick;
  Context context;
  public static TextView textadmin;
   public static List<Retrivedata> filteredList = new ArrayList<>();
    int pos=3;
    AdRequest adRequest;

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                filteredList.clear();
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    array = mArrayList;
                } else {
                    for (Retrivedata androidVersion : mArrayList) {
                        if (androidVersion.getName().toLowerCase().contains(charString)) {
                            filteredList.add(androidVersion);
                        }
                    }
                    array = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = array;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                array = (List<Retrivedata>) filterResults.values;
                // Adapteritems.this.notify();
                array = (List<Retrivedata>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }


    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        public ImageView image;
        ImageView imageedit,image_report;
        ImageView imgdelete;
        ProgressBar progross;
        private AdView adView;

        CardView itemcard;
      public   TextView textname,textprice,textgovern,descrption,textdate,textadmin,user_name;
        public MyViewHolder(View view) {
            super(view);
            image =  view.findViewById(R.id.product_image);
            textadmin=view.findViewById(R.id.admintext);
            imageedit=view.findViewById(R.id.image_edit);
            image_report=view.findViewById(R.id.image_report);
            adView = view.findViewById(R.id.adView);
            imgdelete=view.findViewById(R.id.image_delete);
            textname= view.findViewById(R.id.product_name);
            textprice= view.findViewById(R.id.price);
            textgovern= view.findViewById(R.id.govern);
            descrption= view.findViewById(R.id.descrption);
            textdate=view.findViewById(R.id.textdate);
            itemcard=view.findViewById(R.id.itemcard);
            progross=view.findViewById(R.id.progross);
            user_name=view.findViewById(R.id.user_name);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(itemclick!=null)
            itemclick.Callback(view,getAdapterPosition());

        }
    }
    public Adapteritems( List<Retrivedata> moviesList , Context context) {

        this.array = moviesList;
        this.context=context;
        mArrayList = moviesList;
        setHasStableIds(true);
    }
    public void setClickListener(itemViewinterface itemClickListener) {
        this.itemclick = itemClickListener;
    }
    public void setClickButton(imgclick btnclic){
        this.btnclick=btnclic;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        textadmin=itemView.findViewById(R.id.admintext);
        return new MyViewHolder(itemView);
    }




    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
          if(position==pos){
              pos+=4;

              if(!holder.adView.isShown()) {
                  adRequest = new AdRequest.Builder().build();

                  holder.adView.loadAd(adRequest);
                  holder.adView.setVisibility(View.VISIBLE);
              }

          }else {
//              holder.adView.setVisibility(View.GONE);
          }

        Retrivedata y=array.get(position);
       holder.user_name.setText(y.getUser_name());
        if(!filteredList.isEmpty()){
            holder.textadmin.setVisibility(View.INVISIBLE);
        }
        if(!filteredList.isEmpty()){
            holder.textadmin.setVisibility(View.INVISIBLE);
        }
            Boolean admin=y.getAdmin();
            if(admin){
                holder.textadmin.setVisibility(View.VISIBLE);

            }else {
                holder.textadmin.setVisibility(View.INVISIBLE);
            }

            String token=y.getSocial_id();
            if(token.equals(SharedPrefManager.getInstance(context).getSocialId())){
                holder.imageedit.setVisibility(View.VISIBLE);
                holder.imgdelete.setVisibility(View.VISIBLE);
                holder.image_report.setVisibility(View.INVISIBLE);
            }else {
                holder.imageedit.setVisibility(View.INVISIBLE);
                holder.imgdelete.setVisibility(View.INVISIBLE);
                holder.image_report.setVisibility(View.VISIBLE);

            }
            holder.descrption.setText(y.getDiscrption());
            String textdate=y.getDate();
            holder.textdate.setText(textdate);

            String text=y.getName();
            holder.textname.setText(text);

            String textprice=y.getDiscount();
            holder.textprice.setText(textprice+" "+y.getCurrency() );

            String textgovern=y.getGovern();
            holder.textgovern.setText(textgovern);

//            String textphone=y.getPhone();
//            holder.textphone.setText(textphone);

            String img1 = y.getImg1();
            String img2 = y.getImg2();
            String img3 = y.getImg3();
            String img4 = y.getImg4();

            if(img1!=null) {
                Uri u = Uri.parse(img1);
               holder.progross.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load( u)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progross.setVisibility(View.GONE);
                                return false;
                            }
                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progross.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(holder.image);

            }else if(img2!=null){
                Uri u = Uri.parse(img2);
                holder.progross.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(u)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progross.setVisibility(View.GONE);
                                return false;
                            }
                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progross.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(holder.image);

            }else if(img3!=null){
                Uri u = Uri.parse(img3);
                holder.progross.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(u)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progross.setVisibility(View.GONE);
                                return false;
                            }
                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                holder.progross.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(holder.image);
            }else if(img4!=null){
                Uri u = Uri.parse(img4);
                holder.progross.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(u)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                holder.progross.setVisibility(View.GONE);
                                return false;
                            }
                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                holder.progross.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(holder.image);

            }else {
                holder.image.setImageResource(R.drawable.no_image);
            }
            holder.imageedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnclick.onClickCallback(view,position);
                }
            });
            holder.imgdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnclick.onClickdelete(view,position);
                }
            });

        holder.image_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ReportsPopup.class);
                intent.putExtra("id",array.get(position).getKey());
                intent.putExtra("username",SharedPrefManager.getInstance(context).getMyName());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
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




