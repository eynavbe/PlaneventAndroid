package com.eynav.planevent_android_app;

import android.app.Activity;
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
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;
import java.util.Map;

public class RegisterClientActivity extends AppCompatActivity {
    private static final int RC_SING_IN = 100;

    EditText clientMail;
    EditText clientPassword;
    Button btnClientregister;
    TextView clicktxtSignupClient;
    SignInButton btnGoogleLogin;
    private FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);

        clientMail = findViewById(R.id.idClientMail);
        clientPassword= findViewById(R.id.idClientPassword);
        btnClientregister = findViewById(R.id.idBtnClientRegister);
        clicktxtSignupClient = findViewById(R.id.idClicktxtSignupClient);
        btnGoogleLogin = findViewById(R.id.btnRegisterGoogleClnt);

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gsio = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gsio);
        clicktxtSignupClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterClientActivity.this, SingupClientActivity.class));
            }
        });
        btnGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent singinIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(singinIntent, RC_SING_IN);
            }
        });
        btnClientregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //input mail and password
                String email = clientMail.getText().toString().trim();
                String password = clientPassword.getText().toString().trim();
                //validation
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    clientMail.setError("Invalid Email");
                    clientMail.setFocusable(true);
                }
                else if(TextUtils.isEmpty(password)){
                    clientPassword.setError("Password cannot be empty");
                    clientPassword.setFocusable(true);
                }
                else{
                    registerclientUser(email, password);
                }
            }
        });
    }

    private void registerclientUser(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            addClientToFirebase(email);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterClientActivity.this, "Registration Error: "+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterClientActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void FirebaseAuthWithGoogle(GoogleSignInAccount account) {
//        String personEmail = account.getEmail();
//        System.out.println("emailllllll   "+personEmail);
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
//                            Toast.makeText(RegisterClientActivity.this, ""+user.getEmail(), Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(SingupClientActivity.this, choosingClientOption.class))
                            addClientToFirebase(user.getEmail());

                        }
                        else{
                            Toast.makeText(RegisterClientActivity.this, "Login Failed..", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterClientActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

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

    private void addClientToFirebase(String email) {
            System.out.println("addEventToFirebase");
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            // Create a new user with a first and last name
            Map<String, Object> client = new HashMap<>();
            client.put("emailClient", email);

            Activity activity = this;
            Loading loadingdialog = new Loading(activity);
            loadingdialog.startLoadingdialog();
            db.collection("client").document(email)
                    .set(client)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            System.out.println("DocumentSnapshot added");
//                        dialog.dismiss();
                            loadingdialog.dismissdialog();
                            SharedPreferences shareType = getSharedPreferences("name", MODE_PRIVATE);

                            shareType.edit().putString("name", email).commit();

                            startActivity(new Intent(RegisterClientActivity.this, ChooseHall.class));


//                            startActivity(new Intent(RegisterClientActivity.this, choosingClientOption.class));
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
