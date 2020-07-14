package com.technoplanet.p360.Adapers;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.technoplanet.p360.Models.CategoryPojo;
import com.technoplanet.p360.R;
import com.technoplanet.p360.SubcatActivity;

import java.util.ArrayList;

/**
 * Created by BHARGAV on 02/10/2018.
 */

public class MyRecAdapter extends RecyclerView.Adapter<MyRecAdapter.Holder> {

    private Context c;
    private ArrayList<CategoryPojo> listN;

    public MyRecAdapter(Context c, ArrayList<CategoryPojo> listN) {
        this.c = c;
        this.listN = listN;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(c);
        View v = layoutInflater.inflate(R.layout.homerow, parent,false);

        Holder holder = new Holder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        CategoryPojo obj = listN.get(position);
        holder.txtTitle.setText(obj.getCategoryName());

        String imgUrl = obj.getCategoryImage();
        Glide.with(c).load(imgUrl).into(holder.imgTitle);

        //    holder.imgTitle.setImageResource(obj.getImg());

       // holder.txtTitle.setText(obj.getTitle());
       // holder.imgTitle.setImageResource(obj.getImg());
    }

    @Override
    public int getItemCount() {
        return listN.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        //to find view by id and listner
        private TextView txtTitle;
        private ImageView imgTitle;

        public Holder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            imgTitle = itemView.findViewById(R.id.imgTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   CategoryPojo pojo1 = listN.get(getAdapterPosition());
                    Intent i = new Intent(c, SubcatActivity.class);
                    i.putExtra("subCid", pojo1.getCategoryId());
                    i.putExtra("urlmain",pojo1.getCategoryImage());//to change main img of subcat
                    c.startActivity(i);//adapter contex

                }
            });
        }
    }
}
