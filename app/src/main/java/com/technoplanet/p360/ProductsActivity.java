package com.technoplanet.p360;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Toast;

import com.technoplanet.p360.Adapers.ProductsRecAdapter;
import com.technoplanet.p360.Async.ServiceAsync;
import com.technoplanet.p360.Models.ProductsPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {

    private RecyclerView mainList;
    private Context c;
    private ArrayList<ProductsPojo> productsPojoArrayList;
    private ProductsRecAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        c = ProductsActivity.this;
        mainList = findViewById(R.id.mainList);


        productsPojoArrayList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ProductsActivity.this, 1);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductsActivity.this,LinearLayoutManager.VERTICAL,false);
        mainList.setLayoutManager(gridLayoutManager);

        adapter = new ProductsRecAdapter(c, productsPojoArrayList);
        mainList.setAdapter(adapter);

        Intent i = getIntent();
        String subCatId = i.getStringExtra("subCid");

        serviceCallForGetProductList(subCatId);
    }

    private void serviceCallForGetProductList(String s) {
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String Productsurl = "http://training2.ncryptedprojects.com/ws/sweta_sep18/web-services/home_product.php";
        //?action=products&subcatId=19

        Uri.Builder b = new Uri.Builder();
        b.appendQueryParameter("action", "product");
        b.appendQueryParameter("subcatId", s);

        new ServiceAsync(Productsurl, new ServiceAsync.OnAsyncResult() {
            @Override
            public void onSuccess(String result) {
                progressDialog.cancel();

                try {
                    JSONObject jsonObject = new JSONObject(result);

                    String strTotal = jsonObject.getString("total");

                    int total = Integer.parseInt(strTotal);

                    if (total > 0) {

                        JSONArray mainArray = jsonObject.getJSONArray("products");

                        for (int i = 0; i < mainArray.length(); i++) {

                            JSONObject objProducts = mainArray.getJSONObject(i);

                            String id = objProducts.getString("id");
                            String Pname = objProducts.getString("productName");
                            String amount = objProducts.getString("amount");
                            String catName = objProducts.getString("categoryName");
                            String rating = objProducts.getString("average_rating");
                            String image = objProducts.getString("image");

                            ProductsPojo pojo = new ProductsPojo(id, Pname, amount, catName, rating, image);
                            productsPojoArrayList.add(pojo);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(c, "No data found", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String result) {
                progressDialog.cancel();
                Toast.makeText(c, result, Toast.LENGTH_SHORT).show();

            }
        }, b).execute();

    }
}
