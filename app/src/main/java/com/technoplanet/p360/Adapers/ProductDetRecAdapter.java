package com.technoplanet.p360.Adapers;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.technoplanet.p360.AddtoCartActivity;
import com.technoplanet.p360.Models.ProductDetPojo;
import com.technoplanet.p360.R;

import java.util.ArrayList;

/**
 * Created by BHARGAV on 02/10/2018.
 */

public class ProductDetRecAdapter extends RecyclerView.Adapter<ProductDetRecAdapter.Holder> {

    private Context c;
    private ArrayList<ProductDetPojo> listN;

    public ProductDetRecAdapter(Context c, ArrayList<ProductDetPojo> listN) {
        this.c = c;
        this.listN = listN;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(c);
        View v = layoutInflater.inflate(R.layout.productdet_row, parent,false);

        Holder holder = new Holder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        ProductDetPojo obj = listN.get(position);
        holder.txtTitle.setText(obj.getProductName());

        String imgUrl = obj.getProductImages();
        Glide.with(c).load(imgUrl).into(holder.imgTitle);

        //    holder.imgTitle.setImageResource(obj.getImg());

        holder.txtMRP.setText(obj.getAmount());
        holder.txtOrgMRP.setText(obj.getOrgAmount());
        holder.Pdescr.setText(obj.getProductDesc());

       // holder.imgTitle.setImageResource(obj.getImg());
    }

    @Override
    public int getItemCount() {
        return listN.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        //to find view by id and listner

        private ImageView imgTitle;
        private TextView txtTitle;
        private TextView txtMRP;
        private TextView txtOrgMRP;
        private Button btnADDCart;
        private TextView Pdescr;

        public Holder(View itemView) {
            super(itemView);
            imgTitle = itemView.findViewById(R.id.imgTitle);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtMRP = itemView.findViewById(R.id.txtMRP);
            txtOrgMRP = itemView.findViewById(R.id.txtOrgMRP);
            btnADDCart = itemView.findViewById(R.id.btnADDCart);
            Pdescr = itemView.findViewById(R.id.Pdescr);

            //to get dynamic subcat
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ProductDetPojo pojo = listN.get(getAdapterPosition());
                    Intent i = new Intent(c, AddtoCartActivity.class);
                    i.putExtra("subCid", pojo.getpId());
                    i.putExtra("urlmain",pojo.getProductImages());//to change main img of subcat
                    c.startActivity(i);//adapter contex

                }
            });

            btnADDCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(c, AddtoCartActivity.class);
                    c.startActivity(i);//adapter contex
                }
            });
        }

    }
}
