package bekya.bekyaa.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import bekya.bekyaa.ChangeLanguage;
import bekya.bekyaa.Interface.Categories_View;
import bekya.bekyaa.ItemAnimation;
import bekya.bekyaa.Model.Category;
import bekya.bekyaa.R;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by HP on 05/06/2018.
 */

public class Categories_Adapter extends RecyclerView.Adapter<Categories_Adapter.MyViewHolder>{

    private List<Category> filteredList=new ArrayList<>();
    View itemView;
    Context con;
    Categories_View categories_view;
    int row_index=0;
    private int animation_type = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView Name;
        CardView Card;
        ImageView image;

        RelativeLayout relative_row;
        public MyViewHolder(View view) {
            super(view);
            Name=view.findViewById(R.id.Title);
            image=view.findViewById(R.id.image);
            Card=view.findViewById(R.id.Card);

        }
    }

    public Categories_Adapter(List<Category> list, Context context,int animation_type){
        this.filteredList=list;
        this.con=context;
        this.animation_type = animation_type;

    }

    public void setClickListener(Categories_View restaurantDetails_view) {
        this.categories_view = restaurantDetails_view;

    }

    @Override
    public Categories_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_category, parent, false);
        return new Categories_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Categories_Adapter.MyViewHolder holder, final int position) {

       if(ChangeLanguage.getLanguage(con).equals("en")){
           holder.Name.setText(filteredList.get(position).getCat_en());
       }else {
           holder.Name.setText(filteredList.get(position).getCat_ar());

       }

        Glide.with(con)
                .load( filteredList.get(position).getImg())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.image);

        holder.Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ChangeLanguage.getLanguage(con).equals("en")){
                    categories_view.cat(filteredList.get(position).getKey(),filteredList.get(position).getCat_en());
                }else {
                    categories_view.cat(filteredList.get(position).getKey(),filteredList.get(position).getCat_ar());

                }

            }
        });


        setAnimation(holder.itemView, position);

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
    private int lastPosition = -1;
    private boolean on_attach = true;

    private void setAnimation(View view, int position) {
        if (position > lastPosition) {
            ItemAnimation.animate(view, on_attach ? position : -1, animation_type);
            lastPosition = position;
        }
    }

}

