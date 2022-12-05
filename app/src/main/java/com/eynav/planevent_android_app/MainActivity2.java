package com.eynav.planevent_android_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {
    Button btnHall,btnClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btnHall = findViewById(R.id.btnHall);
        btnClient = findViewById(R.id.btnClient);
        btnHall.setOnClickListener(l ->{
            Intent homeHall= new Intent(this, MainActivity.class);
            homeHall.putExtra("type","Hall");
            this.startActivity(homeHall);
        });
        btnClient.setOnClickListener(l ->{
            Intent homeHall= new Intent(this, MainActivity.class);
            homeHall.putExtra("type","Client");
            this.startActivity(homeHall);
        });
    }
}