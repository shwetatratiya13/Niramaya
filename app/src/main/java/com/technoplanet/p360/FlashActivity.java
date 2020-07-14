package com.technoplanet.p360;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

public class FlashActivity extends AppCompatActivity {

    private LinearLayout logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);

        logo = findViewById(R.id.logo);

        Animation animation = AnimationUtils.loadAnimation(FlashActivity.this, R.anim.flashanim);
        logo.setAnimation(animation);
        animation.start();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(FlashActivity.this,FirebaseActivity.class);
                startActivity(i);
                finish();

            }
        };

        Timer splash = new Timer();
        splash.schedule(task,4000);
    }
}
