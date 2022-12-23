package com.eynav.planevent_android_app.ui.edit;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.eynav.planevent_android_app.R;

public class ChooseMenuActivity extends Fragment {


    Button buffe, salades, firstDishes, secondDishes, deserts;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



            return inflater.inflate(R.layout.activity_choose_menu, container, false);



    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        buffe = view.findViewById(R.id.idBuffeClnt);
        salades = view.findViewById(R.id.idSaladsClnt);
        firstDishes = view.findViewById(R.id.idFirstDishClnt);
        secondDishes = view.findViewById(R.id.idSecondDishClnt);
        deserts = view.findViewById(R.id.idDesertClnt);

        buffe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new EditChooseFragment();
                Bundle args = new Bundle();
                args.putString("menuEdit", "בופה");
                myFragment.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();
////
            }
        });
        salades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new EditChooseFragment();
                Bundle args = new Bundle();
                args.putString("menuEdit", "סלטים");
                myFragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();
            }
        });

        firstDishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new EditChooseFragment();
                Bundle args = new Bundle();
                args.putString("menuEdit", "מנות ראשונות");
                myFragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();

            }
        });
        secondDishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new EditChooseFragment();
                Bundle args = new Bundle();
                args.putString("menuEdit", "מנות עיקריות");
                myFragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();

            }
        });
        deserts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new EditChooseFragment();
                Bundle args = new Bundle();
                args.putString("menuEdit", "קינוחים");
                myFragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();

            }
        });
    }
}