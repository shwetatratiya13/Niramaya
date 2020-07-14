package com.technoplanet.p360.FragmentUtils;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.technoplanet.p360.Adapers.MyRecAdapter;
import com.technoplanet.p360.Async.ServiceAsync;
import com.technoplanet.p360.CameraActivity;
import com.technoplanet.p360.GmapActivity;
import com.technoplanet.p360.Models.CategoryPojo;
import com.technoplanet.p360.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by BHARGAV on 15/10/2018.
 */

public class HomeFrag extends Fragment {

    //private Context c;
    private RecyclerView mainList;
    private ArrayList<CategoryPojo> categoryPojoArrayList;
    private  MyRecAdapter adapter;
    private ImageView refill;
    private ImageView pillrem;
    private ImageView locate;
    private Button uploadbtn;
    private ImageView imgMain;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_home,container,false);
        //c = HomeFrag.this;
        mainList = v.findViewById(R.id.mainList);

        refill = v.findViewById(R.id.refill);
        pillrem = v.findViewById(R.id.pillrem);
        locate = v.findViewById(R.id.locate);
        uploadbtn = v.findViewById(R.id.uploadbtn);

        imgMain = v.findViewById(R.id.imgMain);
/*
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.mainframeanim);
        imgMain.setAnimation(animation);
        animation.start();*/

        AnimationDrawable animationDrawable = new AnimationDrawable();

        BitmapDrawable frame1 = (BitmapDrawable) getResources().getDrawable(R.drawable.mainpg1);
        animationDrawable.addFrame(frame1, 3000);

        BitmapDrawable frame2 = (BitmapDrawable) getResources().getDrawable(R.drawable.mainpg2);
        animationDrawable.addFrame(frame2, 3000);

        BitmapDrawable frame3 = (BitmapDrawable) getResources().getDrawable(R.drawable.mainpg3);
        animationDrawable.addFrame(frame3, 3000);

        imgMain.setBackgroundDrawable(animationDrawable);

        animationDrawable.setOneShot(false);//do u want repeattion or not
        animationDrawable.start();


        categoryPojoArrayList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        mainList.setLayoutManager(linearLayoutManager);

        adapter = new MyRecAdapter(getActivity(),categoryPojoArrayList);
        mainList.setAdapter(adapter);

        serviceCallForGetCategory();

        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(getActivity(),CameraActivity.class);
                getActivity().startActivity(intent);

            }
        });

        //get activity in intent
        refill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.setPackage("com.google.zxing.client.android");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                getActivity().startActivityForResult(intent,0);

            }
        });
        //not working !
        pillrem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(getActivity(),PillRemFrag.class);
                getActivity().startActivity(intent);

            }
        });

        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(getActivity(), GmapActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == RESULT_OK){
            String contents = intent.getStringExtra("SCAN_RESULT");
            String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
            // Handle successful scan
            Toast.makeText(getActivity(), "Scanner opened successfully", Toast.LENGTH_SHORT).show();
        }else if (resultCode == RESULT_CANCELED) {
            // Handle cancel
            Toast.makeText(getActivity(), "Sorry no scanner available", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    private void serviceCallForGetCategory() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url = "http://training2.ncryptedprojects.com/ws/sweta_sep18/web-services/home_product.php";
        //?action=category

        Uri.Builder b = new Uri.Builder();
        b.appendQueryParameter("action", "category");

        new ServiceAsync(url, new ServiceAsync.OnAsyncResult() {
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
                            String name = objCategory.getString("categoryName");
                            String id = objCategory.getString("categoryId");
                            String image = objCategory.getString("categoryImage");

                            CategoryPojo pojo = new CategoryPojo(name, id, "", image);
                            categoryPojoArrayList.add(pojo);
                        }

                        adapter.notifyDataSetChanged();

                    }
                    else {
                        Toast.makeText(getActivity(), "Sorry..Category is not awailable", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String result) {
                progressDialog.cancel();
                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();

            }
        }, b).execute();

    }
}
