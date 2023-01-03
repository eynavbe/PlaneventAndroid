package com.eynav.planevent_android_app.ui.edit;


import static android.content.Context.MODE_PRIVATE;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.eynav.planevent_android_app.R;


public class EditFragment extends Fragment{

    Button menu, numOfInvated, hallDecoration, others;
    SharedPreferences shareType;
    String typePage;
    String from ="";
    public EditFragment(){

    }
    public EditFragment(String from){
        this.from = from;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        shareType = getContext().getSharedPreferences("type", MODE_PRIVATE);
        typePage = shareType.getString("type", "default if empty");
        if (typePage.equals("Hall")) {
            if (from.equals("")){
                ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("עריכה");
                return inflater.inflate(R.layout.activity_choose_manag_edit, container, false);
            }else {
                return inflater.inflate(R.layout.activity_choose_manag_edit, container, false);
            }
        }else {
            ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("בחירות");
            return inflater.inflate(R.layout.activity_choosing_client_option, container, false);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (typePage.equals("Hall")) {
            if (!from.equals("")){
                TextView txtChooseManag = view.findViewById(R.id.txtChooseManag);
                txtChooseManag.setText("בחירות הלקוח");
            }
            menu = view.findViewById(R.id.idMenuManag);
            numOfInvated = view.findViewById(R.id.idNumberInvatedManag);
            hallDecoration = view.findViewById(R.id.idHallDcrtnManag);
            others = view.findViewById(R.id.idOthersManag);
            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new MenuEditActivity();
                    if (!from.equals("")){
                        myFragment = new MenuEditActivity(from);
                    }
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();
                }
            });
            numOfInvated.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new EditChooseFragment();
                    if (!from.equals("")){
                        myFragment = new EditChooseFragment(from);
                    }
                    Bundle args = new Bundle();
                    args.putString("menuEdit", "אטרקציות");
                    myFragment.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();
                }
            });
            hallDecoration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new EditChooseFragment();
                    if (!from.equals("")){
                        myFragment = new EditChooseFragment(from);
                    }
                    Bundle args = new Bundle();
                    args.putString("menuEdit", "עיצוב אולם");
                    myFragment.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();

                }
            });

            others.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new EditChooseFragment();
                    if (!from.equals("")){
                        myFragment = new EditChooseFragment(from);
                    }
                    Bundle args = new Bundle();
                    args.putString("menuEdit", "שונות");
                    myFragment.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();

                }
            });
        }
        else {
            menu = view.findViewById(R.id.idMenuClnt);
            numOfInvated = view.findViewById(R.id.idNumberInvatedClnt);
            hallDecoration = view.findViewById(R.id.idHallDcrtnClnt);
            others = view.findViewById(R.id.idOthersClnt);
            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new ChooseMenuActivity();
                    if (!from.equals("")){
                        myFragment = new ChooseMenuActivity(from);
                    }
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();
                }
            });

            numOfInvated.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new EditChooseFragment();
                    if (!from.equals("")){
                        myFragment = new EditChooseFragment(from);
                    }
                    Bundle args = new Bundle();
                    args.putString("menuEdit", "אטרקציות");
                    myFragment.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();
                }
            });
            hallDecoration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new EditChooseFragment();
                    if (!from.equals("")){
                        myFragment = new EditChooseFragment(from);
                    }
                    Bundle args = new Bundle();
                    args.putString("menuEdit", "עיצוב אולם");
                    myFragment.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();
                }
            });
            others.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new EditChooseFragment();
                    if (!from.equals("")){
                        myFragment = new EditChooseFragment(from);
                    }
                    Bundle args = new Bundle();
                    args.putString("menuEdit", "שונות");
                    myFragment.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();
                }
            });

        }
    }

}