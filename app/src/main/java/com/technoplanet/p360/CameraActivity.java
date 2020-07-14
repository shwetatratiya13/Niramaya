package com.technoplanet.p360;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CameraActivity extends AppCompatActivity {

    private ImageView camera;
    private ImageView gallery;
    private ImageView Rx;
    private static final int CAMERA_PIC_REQUEST  = 1111;
    private ImageView Imageview01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        camera = findViewById(R.id.camera);
        gallery = findViewById(R.id.gallery);
        Rx = findViewById(R.id.Rx);
        Imageview01 = findViewById(R.id.Imageview01);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,CAMERA_PIC_REQUEST);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Rx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST){
            Bitmap image = (Bitmap) data.getExtras().get("data");
            ImageView imageView = (ImageView) findViewById(R.id.Imageview01);
            imageView.setImageBitmap(image);
        }
    }
}
