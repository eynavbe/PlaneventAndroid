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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eynav.planevent_android_app.ui.event.EventsAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctionsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingupClientActivity extends AppCompatActivity {

    private static final int RC_SING_IN = 100;
    GoogleSignInClient mGoogleSignInClient;

    EditText txtMail, txtPassword;
    Button btnSingup;
    TextView txtGoRegister;
    SignInButton btnGoogleLogin;

    private FirebaseAuth mAuth;
    boolean test = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup_client);

        txtMail = findViewById(R.id.idsingupClientMail);
        txtPassword = findViewById(R.id.idClientSingupPassword);
        btnSingup = findViewById(R.id.idBtnClientsingup);
        txtGoRegister = findViewById(R.id.idClicktxtRegisterClient);
        btnGoogleLogin = findViewById(R.id.btnSignupGoogleClnt);

        GoogleSignInOptions gsio = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gsio);

        mAuth = FirebaseAuth.getInstance();

        txtGoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SingupClientActivity.this, RegisterClientActivity.class));
            }
        });

        btnSingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = txtMail.getText().toString();
                String password = txtPassword.getText().toString().trim();

                if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    txtMail.setError("Invalid Email");
                    txtMail.setFocusable(true);
                }
                else if(TextUtils.isEmpty(password)){
                    txtPassword.setError("Password cannot be empty");
                    txtPassword.setFocusable(true);
                }
                else{
                    loginClientUser(mail, password);
                }
            }
        });

        btnGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent singinIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(singinIntent, RC_SING_IN);
            }
        });

    }

    private void loginClientUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            readClientFromFirebase(email);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SingupClientActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SingupClientActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void readClientFromFirebase(String email) {
        Log.e("readClientFromFirebase","readClientFromFirebase");
        Activity activity = this;
        Loading loadingdialog = new Loading(activity);
        loadingdialog.startLoadingdialog();







        (new CloudFunctions()).readClientsDataFromFirebase("client")
                .addOnCompleteListener(new OnCompleteListener<List<String>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<String>> task) {
                        System.out.println(task.toString());
                        System.out.println(task.getResult().toString());
                        if (task.isSuccessful()) {
                            for (int i = 0; i < task.getResult().size(); i++) {
                                System.out.println(task.getResult().get(i));
                                if (task.getResult().get(i).equals(email)){
                                        test = true;
                                        SharedPreferences shareType = getSharedPreferences("emailClient", MODE_PRIVATE);

                                        shareType.edit().putString("emailClient", email).commit();
                                        startActivity(new Intent(SingupClientActivity.this, ChooseHall.class));
                                        finish();

                                }
                            }
                            loadingdialog.dismissdialog();
                            if (!test){
                                AlertDialog.Builder builderDelete = new AlertDialog.Builder(getApplicationContext())
                                        .setTitle("הוספת משתמש")
                                        .setMessage("רשום לנו שאתה לא רשום כלקוח, היית רוצה להירשם?")
                                        .setIcon(R.drawable.ic_baseline_delete_24)
                                        .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                addClientToFirebase(email);

                                            }
                                        })
                                        .setNegativeButton("לא", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                startActivity(new Intent(SingupClientActivity.this, MainActivity2.class));
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












//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("client")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                if (document.getId().equals(email)){
//                                    test = true;
//                                    SharedPreferences shareType = getSharedPreferences("emailClient", MODE_PRIVATE);
//
//                                    shareType.edit().putString("emailClient", email).commit();
//                                    startActivity(new Intent(SingupClientActivity.this, ChooseHall.class));
//                                    finish();
//                                }
//                            }
//                            loadingdialog.dismissdialog();
//
//                            if (!test){
//                                AlertDialog.Builder builderDelete = new AlertDialog.Builder(getApplicationContext())
//                                        .setTitle("הוספת משתמש")
//                                        .setMessage("רשום לנו שאתה לא רשום כלקוח, היית רוצה להירשם?")
//                                        .setIcon(R.drawable.ic_baseline_delete_24)
//                                        .setPositiveButton("כן", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                addClientToFirebase(email);
//
//                                            }
//                                        })
//                                        .setNegativeButton("לא", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                startActivity(new Intent(SingupClientActivity.this, MainActivity2.class));
//                                                finish();
//                                            }
//                                        });
//
//                                builderDelete.show();
//
//
//                           }
//                        } else {
//                            System.out.println("Error getting documents.");
//                            System.out.println(task.getException().toString());
//                        }
//                    }
//
//
//                });
    }
    private void addClientToFirebase(String email) {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name
//        Map<String, Object> client = new HashMap<>();
//        client.put("emailClient", email);
        Activity activity = this;
        Loading loadingdialog = new Loading(activity);
        loadingdialog.startLoadingdialog();


        new CloudFunctions().writeUserToFirebase("client",  email,  email)
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        System.out.println(task.toString());
                        System.out.println(task.getResult().toString());
                        if (task.isSuccessful()) {
                            Log.e("demo",""+task.getResult());
                            System.out.println("DocumentSnapshot added");
                            loadingdialog.dismissdialog();
                            SharedPreferences shareType = getSharedPreferences("emailClient", MODE_PRIVATE);

                            shareType.edit().putString("emailClient", email).commit();

                            startActivity(new Intent(SingupClientActivity.this, ChooseHall.class));
                            finish();
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




//        db.collection("client").document(email)
//                .set(client)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        System.out.println("DocumentSnapshot added");
//                        loadingdialog.dismissdialog();
//                        SharedPreferences shareType = getSharedPreferences("emailClient", MODE_PRIVATE);
//
//                        shareType.edit().putString("emailClient", email).commit();
//
//                        startActivity(new Intent(SingupClientActivity.this, ChooseHall.class));
//                        finish();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        System.out.println("Error adding document");
//                        System.out.println(e.toString());
//                    }
//                });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SING_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    FirebaseAuthWithGoogle(account);





                } catch (ApiException e) {
                    Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void FirebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SingupClientActivity.this, ""+user.getEmail(), Toast.LENGTH_SHORT).show();
                            readClientFromFirebase(user.getEmail());

                        }
                        else{
                            Toast.makeText(SingupClientActivity.this, "Login Failed..", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SingupClientActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

}