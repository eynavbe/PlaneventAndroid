package com.eynav.planevent_android_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
        Intent intent = getIntent();
        String emailClient = intent.getStringExtra("emailClient");

        halls.add(new Hall("aaaa","aaaa","tel aviv",888));
        readHallsFromFirebase(emailClient);
        rvListedHalls.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        hallAdapter = new HallsAdapter(this.getApplicationContext(),halls, emailClient);
        rvListedHalls.setAdapter(hallAdapter);
    }

    private void readHallsFromFirebase(String emailClient) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("hall")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getId());

                                String nameHall = String.valueOf(document.getData().get("nameHall"));
                                String hallImage = String.valueOf(document.getData().get("hallImage"));
                                String hallArea = String.valueOf(document.getData().get("hallArea"));
                                Integer hallCountPeople = (Integer)(document.getData().get("hallCountPeople"));
                                Hall hall = new Hall(nameHall,hallImage,hallArea,hallCountPeople);
                                halls.add(hall);
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            rvListedHalls.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            hallAdapter = new HallsAdapter(getApplicationContext(),halls, emailClient);
                            rvListedHalls.setAdapter(hallAdapter);
                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
                            System.out.println("Error getting documents.");
                            System.out.println(task.getException().toString());
                        }
                    }});
    }
}