package com.eynav.planevent_android_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ScreenLogo extends AppCompatActivity {

    ImageView app_logo;
    Animation top,bottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.screen_logo);

        app_logo = findViewById(R.id.app_logo);

        top = AnimationUtils.loadAnimation(this,R.anim.top);

        app_logo.setAnimation(top);

        int SPLASH_SCREEN = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ScreenLogo.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);

    }
}