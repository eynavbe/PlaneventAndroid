package com.eynav.planevent_android_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctionsException;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChooseHall extends AppCompatActivity {
    RecyclerView rvListedHalls;
    List<Hall> halls = new ArrayList<>();
    HallsAdapter hallAdapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_hall);
        rvListedHalls = findViewById(R.id.rvListedHalls);
        SharedPreferences shareName = this.getApplicationContext().getSharedPreferences("emailClient", MODE_PRIVATE);
        String emailClient = shareName.getString("emailClient", "default if empty");
        context =this;
        readHallsFromFirebase(emailClient);
        rvListedHalls.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        hallAdapter = new HallsAdapter(this,halls, emailClient);
        rvListedHalls.setAdapter(hallAdapter);
    }

    private void readHallsFromFirebase(String emailClient) {
//        (new CloudFunctions()).readHallsDataFromFirebaseCloud("hall", rvListedHalls, emailClient, context);
        (new CloudFunctions()).readHallsDataFromFirebase("hall")
                .addOnCompleteListener(new OnCompleteListener<List<Hall>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Hall>> task) {
                        System.out.println(task.toString());
                        System.out.println(task.getResult().toString());
                        if (task.isSuccessful()) {
                            for (int i = 0; i < task.getResult().size(); i++) {
                                System.out.println(task.getResult().get(i));
                            }
                            rvListedHalls.setLayoutManager(new LinearLayoutManager(context));
                            hallAdapter = new HallsAdapter(context,task.getResult(), emailClient);
                            rvListedHalls.setAdapter(hallAdapter);
                        }else{
                            Exception e = task.getException();
                            if (e instanceof FirebaseFunctionsException) {
                                FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                                FirebaseFunctionsException.Code code = ffe.getCode();
                                Object details = ffe.getDetails();
                                System.out.println(details.toString());
                            }
                        }
                    }
                });



        //        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("hall")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                String nameHall = String.valueOf(document.getData().get("hallName"));
//                                String hallImage = String.valueOf(document.getData().get("hallImage"));
//                                String hallArea = String.valueOf(document.getData().get("hallArea"));
//                                String hallCountPeople = (String)(document.getData().get("maxHallPeople"));
//                                String email = String.valueOf(document.getData().get("email"));
//                                String phoneNum = String.valueOf(document.getData().get("phoneNum"));
//                                Hall hall = new Hall(nameHall,hallImage,hallArea,hallCountPeople,email,phoneNum);
//                                halls.add(hall);
//                            }
//                            rvListedHalls.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                            hallAdapter = new HallsAdapter(context,halls, emailClient);
//                            rvListedHalls.setAdapter(hallAdapter);
//                        } else {
//                            System.out.println("Error getting documents.");
//                        }
//                    }});
    }
}