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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_hall);
        rvListedHalls = findViewById(R.id.rvListedHalls);
        halls.add(new Hall("aaaa","aaaa","tel aviv",888));
        rvListedHalls.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        hallAdapter = new HallsAdapter(this.getApplicationContext(),halls);
        rvListedHalls.setAdapter(hallAdapter);
    }
}