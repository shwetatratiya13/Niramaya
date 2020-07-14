package com.technoplanet.p360;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.technoplanet.p360.Adapers.SubcatRecAdapter;
import com.technoplanet.p360.Async.ServiceAsync;
import com.technoplanet.p360.Models.SubcatagoryPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubcatActivity extends AppCompatActivity {

    private RecyclerView mainList;
    private Context c;
    private ArrayList<SubcatagoryPojo> categoryPojoArrayList;
    private SubcatRecAdapter adapter;
    private ImageView imgMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcat);

        c = SubcatActivity.this;
        mainList = findViewById(R.id.mainList);
        imgMain = findViewById(R.id.imgMain);

        categoryPojoArrayList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SubcatActivity.this,LinearLayoutManager.VERTICAL,false);
        mainList.setLayoutManager(linearLayoutManager);


        adapter = new SubcatRecAdapter(c,categoryPojoArrayList);
        mainList.setAdapter(adapter);

        Intent i = getIntent();
        String subCatid = i.getStringExtra("subCid");
        String subCatimg = i.getStringExtra("urlmain");//to change main img

        Glide.with(c).load(subCatimg).into(imgMain);

        serviceCallForGetSubCategory(subCatid);

    }

    private void serviceCallForGetSubCategory(String s) {
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String subCaturl = "http://training2.ncryptedprojects.com/ws/sweta_sep18/web-services/home_product.php";
        //?action=subcategory&catId=1

        Uri.Builder b = new Uri.Builder();
        b.appendQueryParameter("action", "subcategory");
        b.appendQueryParameter("catId", s);

        new ServiceAsync(subCaturl, new ServiceAsync.OnAsyncResult() {
            @Override
            public void onSuccess(String result) {
                progressDialog.cancel();

                try {
                    JSONArray mainArray = new JSONArray(result);
                    JSONObject obj1 = mainArray.getJSONObject(0);

                    String strStatus = obj1.getString("status");
                    String strStatusCode = obj1.getString("statusCode");

                    if ( strStatus.equals("valid") && strStatusCode.equals("1") ){

                        for (int i = 0; i < mainArray.length() - 1; i++ )  {
                            JSONObject objCategory =  mainArray.getJSONObject(i+1);
                            String name = objCategory.getString("subCategoryName");
                            String id = objCategory.getString("subCategoryId");
                            String image = objCategory.getString("categoryImage");

                            SubcatagoryPojo pojo = new SubcatagoryPojo(name, id, "", image);
                            categoryPojoArrayList.add(pojo);
                        }

                        adapter.notifyDataSetChanged();

                    }
                    else {
                        Toast.makeText(c, "Sorry..Category is not awailable", Toast.LENGTH_SHORT).show();
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
