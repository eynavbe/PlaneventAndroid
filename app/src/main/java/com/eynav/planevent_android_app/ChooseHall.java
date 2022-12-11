package com.eynav.planevent_android_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.eynav.planevent_android_app.ui.event.EventsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ChooseHall extends AppCompatActivity {
    RecyclerView rvListedHalls;
    List<Hall> halls = new ArrayList<>();
    HallsAdapter hallAdapter;
    SharedPreferences shareType;
    String typePage;

    @Override
    public void onStart() {
        super.onStart();
        shareType = this.getApplicationContext().getSharedPreferences("type", MODE_PRIVATE);
        typePage = shareType.getString("type", "default if empty");
        if (typePage.equals("Hall")){
            ((AppCompatActivity) this.getApplicationContext()).getSupportActionBar().setTitle("אירועים");
        }else {
            ((AppCompatActivity) this.getApplicationContext()).getSupportActionBar().setTitle("בחירות");

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_hall);
        rvListedHalls = findViewById(R.id.rvListedHalls);

        rvListedHalls.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        hallAdapter = new HallsAdapter(this.getApplicationContext(),halls);
        rvListedHalls.setAdapter(hallAdapter);
    }
}