package com.eynav.planevent_android_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {
    Button btnHall,btnClient;
    SharedPreferences shareType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btnHall = findViewById(R.id.btnHall);
        btnClient = findViewById(R.id.btnClient);
        btnHall.setOnClickListener(l ->{
            Intent homeHall= new Intent(this, MainActivity.class);
            // get or create SharedPreferences
            shareType = getSharedPreferences("type", MODE_PRIVATE);

            // save your string in SharedPreferences
            shareType.edit().putString("type", "Hall").commit();
            this.startActivity(homeHall);
        });
        btnClient.setOnClickListener(l ->{
            Intent homeHall= new Intent(this, MainActivity.class);
            // get or create SharedPreferences
            shareType = getSharedPreferences("type", MODE_PRIVATE);

            // save your string in SharedPreferences
            shareType.edit().putString("type", "Client").commit();
            this.startActivity(homeHall);
        });
    }
}