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
import com.technoplanet.p360.Models.SubcatagoryPojo;
import com.technoplanet.p360.ProductsActivity;
import com.technoplanet.p360.R;

import java.util.ArrayList;

/**
 * Created by BHARGAV on 02/10/2018.
 */

public class AddtoCartRecAdapter extends RecyclerView.Adapter<AddtoCartRecAdapter.Holder> {

    private Context c;
    private ArrayList<SubcatagoryPojo> listN;

    public AddtoCartRecAdapter(Context c, ArrayList<SubcatagoryPojo> listN) {
        this.c = c;
        this.listN = listN;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(c);
        View v = layoutInflater.inflate(R.layout.subcat_row, parent,false);

        Holder holder = new Holder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        SubcatagoryPojo obj = listN.get(position);
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

            //to get dynamic subcat
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SubcatagoryPojo pojo = listN.get(getAdapterPosition());
                    Intent i = new Intent(c, ProductsActivity.class);
                    i.putExtra("subCid", pojo.getCategoryId());
                    c.startActivity(i);//adapter contex

                }
            });
        }
    }
}
