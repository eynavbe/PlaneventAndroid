package com.eynav.planevent_android_app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterHallActivity extends AppCompatActivity {


    EditText hallName, hallArea, maxHallPeople, phoneNum, email, password;
    Button btnRegister;
    TextView clicktxtSignupManag;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_hall);

        hallName = findViewById(R.id.idHallName);
        hallArea = findViewById(R.id.idAreaHall);
        maxHallPeople = findViewById(R.id.idNumberInvtManag);
        phoneNum = findViewById(R.id.idManagePhoneNum);
        email = findViewById(R.id.idManagMail);
        password= findViewById(R.id.idManagPassword);
        btnRegister = findViewById(R.id.idBtnManagRegister);
        clicktxtSignupManag = findViewById(R.id.idClicktxtSignupManag);

        mAuth = FirebaseAuth.getInstance();

        clicktxtSignupManag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterHallActivity.this, SignupHallActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //input mail and password
                String mail = email.getText().toString().trim();
                String psswrd = password.getText().toString().trim();
                //validation
                if(TextUtils.isEmpty(psswrd)){
                    password.setError("Password cannot be empty");
                    password.setFocusable(true);
                }
                else{
                    registerManagUser(mail, psswrd);
                }
            }
        });
    }

    private void registerManagUser(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
//                            Toast.makeText(RegisterHallActivity.this, "Registered... \n"+user.getEmail(), Toast.LENGTH_SHORT).show();
                            addHallToFirebase();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterHallActivity.this, "Registration Error: "+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterHallActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addHallToFirebase() {
        System.out.println("addEventToFirebase");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name
        Map<String, Object> hall = new HashMap<>();
        hall.put("hallName", hallName.getText().toString());
        hall.put("hallArea", hallArea.getText().toString());
        hall.put("maxHallPeople", maxHallPeople.getText().toString());
        hall.put("phoneNum", phoneNum.getText().toString());
        hall.put("email", email.getText().toString());

        Activity activity = this;
        Loading loadingdialog = new Loading(activity);
        loadingdialog.startLoadingdialog();
//        hallName = findViewById(R.id.idHallName);
//        hallArea = findViewById(R.id.idAreaHall);
//        maxHallPeople = findViewById(R.id.idNumberInvtManag);
//        phoneNum = findViewById(R.id.idManagePhoneNum);
//        email = findViewById(R.id.idManagMail);
//        password= findViewById(R.id.idManagPassword);
//        btnRegister = findViewById(R.id.idBtnManagRegister);
//        clicktxtSignupManag = findViewById(R.id.idClicktxtSignupManag);
        db.collection("hall").document(hallName.getText().toString())
                .set(hall)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("DocumentSnapshot added");
//                        dialog.dismiss();
                        loadingdialog.dismissdialog();
                        SharedPreferences shareType = getSharedPreferences("name", MODE_PRIVATE);

                        // save your string in SharedPreferences
                        shareType.edit().putString("name", hallName.getText().toString()).commit();
                        startActivity(new Intent(RegisterHallActivity.this, MainActivity.class));

//                            startActivity(new Intent(RegisterHallActivity.this, ChooseManagEditActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error adding document");
                        System.out.println(e.toString());
                    }
                });

    }
}