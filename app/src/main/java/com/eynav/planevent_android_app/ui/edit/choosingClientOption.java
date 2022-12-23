package com.eynav.planevent_android_app.ui.edit;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class choosingClientOption  extends Fragment {

    Button menu, numOfInvated, hallDecoration, others;
    SharedPreferences shareType;
    String typePage;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        shareType = getContext().getSharedPreferences("type", MODE_PRIVATE);
        typePage = shareType.getString("type", "default if empty");
        if (typePage.equals("Hall")) {

            ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("עריכה");
            return inflater.inflate(R.layout.activity_choose_manag_edit, container, false);

        }else {
            ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("בחירות");

            return inflater.inflate(R.layout.activity_choosing_client_option, container, false);

        }

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (typePage.equals("Hall")) {
            menu = view.findViewById(R.id.idMenuManag);
            numOfInvated = view.findViewById(R.id.idNumberInvatedManag);
            hallDecoration = view.findViewById(R.id.idHallDcrtnManag);
            others = view.findViewById(R.id.idOthersManag);

            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new MenuEditActivity();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();
//

                }
            });
            numOfInvated.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new EditChooseFragment();
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
                    Bundle args = new Bundle();
                    args.putString("menuEdit", "עיצוב אולם");
                    myFragment.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();

                }
            });
            others.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    startActivity(new Intent(ChooseManagEditActivity.this, choosingClientOption.class));
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new ChooseMenuActivity();
                    Bundle args = new Bundle();
                    args.putString("menuEdit", "שונות");
                    myFragment.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();
//

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
//                    ChooseMenuActivity nextFrag= new ChooseMenuActivity();
//                    getActivity().getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.nav_host_fragment_activity_main, nextFrag, "findThisFragment")
//                            .addToBackStack(null)
//                            .commit();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new ChooseMenuActivity();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();
//                    startActivity(new Intent(choosingClientOption.this, ChooseMenuActivity.class));
                }
            });
            numOfInvated.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new EditChooseFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();
                }
            });
            hallDecoration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new EditChooseFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();
                }
            });
            //TODO class to go after click 'others'
            others.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    startActivity(new Intent(choosingClientOption.this, choosingClientOption.class));
                }
            });

        }
    }

}