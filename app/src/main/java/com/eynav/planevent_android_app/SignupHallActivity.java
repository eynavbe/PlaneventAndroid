package com.eynav.planevent_android_app;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctionsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignupHallActivity extends AppCompatActivity {

    EditText txtMail, txtPassword;
    Button btnSingup;
    TextView txtGoRegister;

    private FirebaseAuth mAuth;
    boolean test = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_hall);

        txtMail = findViewById(R.id.idManagtMail);
        txtPassword = findViewById(R.id.idManagPassword);
        btnSingup = findViewById(R.id.idBtnManagSignup);
        txtGoRegister = findViewById(R.id.idClicktxtRegisterManag);

        mAuth = FirebaseAuth.getInstance();

        txtGoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupHallActivity.this, RegisterHallActivity.class));
            }
        });

        btnSingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = txtMail.getText().toString();
                String password = txtPassword.getText().toString().trim();

                 if(TextUtils.isEmpty(password)){
                    txtPassword.setError("Password cannot be empty");
                    txtPassword.setFocusable(true);
                }

                else{
                    loginManagerUser(mail, password);
                }
            }
        });

    }

    private void loginManagerUser(String email, String password) {





        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            readHallFromFirebase(email);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignupHallActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignupHallActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void readHallFromFirebase(String email) {
        Log.e("readClientFromFirebase","readClientFromFirebase");
        Activity activity = this;
        Loading loadingdialog = new Loading(activity);
        loadingdialog.startLoadingdialog();
        List<Event> events = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        (new CloudFunctions()).readHallsDataFromFirebase("hall")
                .addOnCompleteListener(new OnCompleteListener<List<Hall>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Hall>> task) {
                        System.out.println(task.toString());
                        System.out.println(task.getResult().toString());
                        if (task.isSuccessful()) {
                            for (int i = 0; i < task.getResult().size(); i++) {
                                System.out.println(task.getResult().get(i));
                                if (task.getResult().get(i).getEmail().equals(email)){
                                    test = true;
                                    SharedPreferences shareType = getSharedPreferences("name", MODE_PRIVATE);
//                                    String hallName = String.valueOf(document.getData().get("hallName"));
                                    // save your string in SharedPreferences
                                    shareType.edit().putString("name", task.getResult().get(i).getNameHall()).commit();
                                    startActivity(new Intent(SignupHallActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                            loadingdialog.dismissdialog();

                            if (!test){
                                AlertDialog.Builder builderDelete = new AlertDialog.Builder(getApplicationContext())
                                        .setTitle("הוספת משתמש")
                                        .setMessage("רשום לנו שאתה לא רשום כבעל אולם, היית רוצה להירשם?")
                                        .setIcon(R.drawable.ic_baseline_delete_24)
                                        .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                startActivity(new Intent(SignupHallActivity.this, RegisterHallActivity.class));
                                                finish();

                                            }
                                        })
                                        .setNegativeButton("לא", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                startActivity(new Intent(SignupHallActivity.this, MainActivity2.class));
                                                finish();
                                            }
                                        });

                                builderDelete.show();


                            }
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



//        db.collection("hall")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                System.out.println(document.getId());
//
//                                String emailHall = String.valueOf(document.getData().get("email"));
//                                if (emailHall.equals(email)){
//                                    test = true;
//                                    SharedPreferences shareType = getSharedPreferences("name", MODE_PRIVATE);
//                                    String hallName = String.valueOf(document.getData().get("hallName"));
//                                    // save your string in SharedPreferences
//                                    shareType.edit().putString("name", hallName).commit();
//                                    startActivity(new Intent(SignupHallActivity.this, MainActivity.class));
//                                    finish();
//                                }
//                            }
//                            loadingdialog.dismissdialog();
//
//                            if (!test){
//                                AlertDialog.Builder builderDelete = new AlertDialog.Builder(getApplicationContext())
//                                        .setTitle("הוספת משתמש")
//                                        .setMessage("רשום לנו שאתה לא רשום כבעל אולם, היית רוצה להירשם?")
//                                        .setIcon(R.drawable.ic_baseline_delete_24)
//                                        .setPositiveButton("כן", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                startActivity(new Intent(SignupHallActivity.this, RegisterHallActivity.class));
//                                                finish();
//
//                                            }
//                                        })
//                                        .setNegativeButton("לא", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                startActivity(new Intent(SignupHallActivity.this, MainActivity2.class));
//                                                finish();
//                                            }
//                                        });
//
//                                builderDelete.show();
//
//
//                            }
//                        } else {
//                            System.out.println("Error getting documents.");
//                            System.out.println(task.getException().toString());
//                        }
//                    }
//
//
//                });
    }


}