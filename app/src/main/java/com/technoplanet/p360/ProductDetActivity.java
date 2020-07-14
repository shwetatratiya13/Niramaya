package com.technoplanet.p360;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import com.technoplanet.p360.Adapers.ProductDetRecAdapter;
import com.technoplanet.p360.Async.ServiceAsync;
import com.technoplanet.p360.Models.ProductDetPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductDetActivity extends AppCompatActivity {

    private RecyclerView mainList;
    private Context c;
    private ArrayList<ProductDetPojo> productDetPojoArrayList;
    private ProductDetRecAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_det);

        c = ProductDetActivity.this;
        mainList = findViewById(R.id.mainList);

        productDetPojoArrayList = new ArrayList<>();
       // GridLayoutManager gridLayoutManager = new GridLayoutManager(ProductsActivity.this, 1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductDetActivity.this,LinearLayoutManager.VERTICAL,false);
        mainList.setLayoutManager(linearLayoutManager);

        adapter = new ProductDetRecAdapter(c, productDetPojoArrayList);
        mainList.setAdapter(adapter);

        Intent i = getIntent();
        String poductId = i.getStringExtra("subCid");
       // String poductimg = i.getStringExtra("urlmain");

      // Glide.with(c).load(poductimg).into(imgTitle);

        serviceCallForGetProductDetail(poductId);

    }
    private void serviceCallForGetProductDetail(String s) {
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String ProductDetsurl = "http://training2.ncryptedprojects.com/ws/sweta_sep18/web-services/product_detail.php";
        //?pId=1

        Uri.Builder b = new Uri.Builder();
       // b.appendQueryParameter("action", "products");
        b.appendQueryParameter("pId", s);

        new ServiceAsync(ProductDetsurl, new ServiceAsync.OnAsyncResult() {
            @Override
            public void onSuccess(String result) {
                progressDialog.cancel();

                try {
                    JSONObject jsonObject = new JSONObject(result);

                    String strStatus = jsonObject.getString("status");
                    String strStatusCode = jsonObject.getString("statusCode");

                    if (strStatus.equals("valid") && strStatusCode.equals("1")) {

                        for (int i = 0; i < jsonObject.length()-1; i++) {

                           //JSONObject objProductDet = jsonObject.getJSONObject("");

                            String Pid = jsonObject.getString("pId");
                            String Pname = jsonObject.getString("productName");
                            String Pdescr = jsonObject.getString("productDesc");
                            String amount = jsonObject.getString("amount");
                            String Oamount = jsonObject.getString("orgAmount");
                            String Quan = jsonObject.getString("quantity");
                            String Cname = jsonObject.getString("catname");
                            String SubCatname = jsonObject.getString("subcatname");

                            JSONArray array = jsonObject.getJSONArray("productImages");
                            JSONArray imgarray = array.getJSONArray(0);
                            String image = "" + imgarray ;
                            ProductDetPojo pojo = new ProductDetPojo(Pid, Pname,Pdescr, amount,Oamount,Quan,Cname,SubCatname,image);
                            productDetPojoArrayList.add(pojo);
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
