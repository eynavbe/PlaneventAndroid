package com.eynav.planevent_android_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity2 extends AppCompatActivity {
    Button btnHall,btnClient;
    SharedPreferences shareType;
    FirebaseAuth mAuth;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        btnHall = findViewById(R.id.btnHall);
        btnClient = findViewById(R.id.btnClient);
        btnHall.setOnClickListener(l ->{
            // get or create SharedPreferences
            shareType = getSharedPreferences("type", MODE_PRIVATE);

            // save your string in SharedPreferences
            shareType.edit().putString("type", "Hall").commit();

            if(user == null){
                startActivity(new Intent(MainActivity2.this, RegisterHallActivity.class));
            }
            else{
                startActivity(new Intent(MainActivity2.this, SignupHallActivity.class));
            }
        });
        btnClient.setOnClickListener(l ->{
            // get or create SharedPreferences
            shareType = getSharedPreferences("type", MODE_PRIVATE);

            // save your string in SharedPreferences
            shareType.edit().putString("type", "Client").commit();


            if(user == null){
                startActivity(new Intent(MainActivity2.this, RegisterClientActivity.class));
            }
            else{
                startActivity(new Intent(MainActivity2.this, SingupClientActivity.class));
            }
        });
    }
}