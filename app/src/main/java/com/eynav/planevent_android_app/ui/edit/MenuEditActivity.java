package com.eynav.planevent_android_app.ui.edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.eynav.planevent_android_app.R;

public class MenuEditActivity extends Fragment {

    Button buffe, salades, firstDishes, secondDishes, deserts;
    String from = "";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.activity_menu_edit, container, false);
    }
    public MenuEditActivity(){

    }
    public MenuEditActivity(String from){
        this.from = from;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buffe = view.findViewById(R.id.idBuffeManag);
        salades = view.findViewById(R.id.idSaladsManag);
        firstDishes = view.findViewById(R.id.idFirstDishManag);
        secondDishes = view.findViewById(R.id.idSecondDishManag);
        deserts = view.findViewById(R.id.idDesertManag);
        buffe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new EditChooseFragment();
                if (!from.equals("")){
                    myFragment = new EditChooseFragment(from);
                }
                Bundle args = new Bundle();
                args.putString("menuEdit", "בופה");
                myFragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();
            }
        });
        salades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new EditChooseFragment();
                if (!from.equals("")){
                    myFragment = new EditChooseFragment(from);
                }
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
                if (!from.equals("")){
                    myFragment = new EditChooseFragment(from);
                }
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
                if (!from.equals("")){
                    myFragment = new EditChooseFragment(from);
                }
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
                if (!from.equals("")){
                    myFragment = new EditChooseFragment(from);
                }
                Bundle args = new Bundle();
                args.putString("menuEdit", "קינוחים");
                myFragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();
            }
        });
    }
}