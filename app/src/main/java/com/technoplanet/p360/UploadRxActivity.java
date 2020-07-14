package com.technoplanet.p360;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class UploadRxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_rx);

        //for back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
