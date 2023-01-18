package com.eynav.planevent_android_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.eynav.planevent_android_app.ui.edit.ChooseMenuActivity;
import com.eynav.planevent_android_app.ui.edit.EditFragment;
import com.eynav.planevent_android_app.ui.event.EventFragment;
import com.eynav.planevent_android_app.ui.home.HomeFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.eynav.planevent_android_app.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
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
            signOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this, MainActivity2.class));

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_event, R.id.navigation_edit)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        navView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Fragment homeFragment = new HomeFragment();
                    this.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, homeFragment).addToBackStack(null).commit();
                    break;
                case R.id.navigation_event:
                    Fragment eventFragment = new EventFragment();
                    this.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, eventFragment).addToBackStack(null).commit();
                    break;
                case R.id.navigation_edit:
                    Fragment editFragment = new EditFragment();
                    this.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, editFragment).addToBackStack(null).commit();
                    break;
                default:
                    System.out.println("llllllllll");
            }
            return true; // return true;

        });
    }
}