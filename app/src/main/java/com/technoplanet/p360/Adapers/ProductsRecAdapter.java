package com.technoplanet.p360.Adapers;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.technoplanet.p360.Models.ProductsPojo;
import com.technoplanet.p360.ProductDetActivity;
import com.technoplanet.p360.R;

import java.util.ArrayList;

/**
 * Created by BHARGAV on 02/10/2018.
 */

public class ProductsRecAdapter extends RecyclerView.Adapter<ProductsRecAdapter.Holder> {

    private Context c;
    private ArrayList<ProductsPojo> listN;

    public ProductsRecAdapter(Context c, ArrayList<ProductsPojo> listN) {
        this.c = c;
        this.listN = listN;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(c);
        View v = layoutInflater.inflate(R.layout.products_row, parent,false);

        Holder holder = new Holder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        ProductsPojo obj = listN.get(position);
        holder.txtTitle.setText(obj.getProductName());

        String imgUrl = obj.getImage();
        Glide.with(c).load(imgUrl).into(holder.imgTitle);

        holder.txtMRP.setText(obj.getAmount());
        holder.rating.setRating(Float.parseFloat(obj.getAverage_rating()));
    }

    @Override
    public int getItemCount() {
        return listN.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        //to find view by id and listner
        private TextView txtTitle;
        private ImageView imgTitle;
        private TextView txtMRP;
        private Button btnADD;
        private RatingBar rating;

        public Holder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            imgTitle = itemView.findViewById(R.id.imgTitle);
            txtMRP = itemView.findViewById(R.id.txtMRP);
            btnADD = itemView.findViewById(R.id.btnADD);
            rating = itemView.findViewById(R.id.rating);

            //to get dynamic subcat
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ProductsPojo pojo = listN.get(getAdapterPosition());
                    Intent i = new Intent(c, ProductDetActivity.class);
                    i.putExtra("subCid", pojo.getId());
                    c.startActivity(i);//adapter context

                }
            });
        }
    }
}
