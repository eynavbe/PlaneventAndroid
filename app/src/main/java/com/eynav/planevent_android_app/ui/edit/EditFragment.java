package com.eynav.planevent_android_app.ui.edit;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eynav.planevent_android_app.R;
import com.github.drjacky.imagepicker.ImagePicker;
import com.github.drjacky.imagepicker.constant.ImageProvider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class EditFragment extends Fragment{

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
            //TODO class to go after click 'numOfInvated'
            numOfInvated.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    startActivity(new Intent(ChooseManagEditActivity.this, choosingClientOption.class));

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new EditChooseFragment();
                    Bundle args = new Bundle();
                    args.putString("menuEdit", "אטרקציות");
                    myFragment.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();

                }
            });
            //TODO class to go after click 'hallDecoration'
            hallDecoration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    startActivity(new Intent(ChooseManagEditActivity.this, choosingClientOption.class));
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new EditChooseFragment();
                    Bundle args = new Bundle();
                    args.putString("menuEdit", "עיצוב אולם");
                    myFragment.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();

                }
            });
            //TODO class to go after click 'others'
            others.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new EditChooseFragment();
                    Bundle args = new Bundle();
                    args.putString("menuEdit", "שונות");
                    myFragment.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();

//                    startActivity(new Intent(ChooseManagEditActivity.this, choosingClientOption.class));
//                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                    Fragment myFragment = new ChooseMenuActivity();
//                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();
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
            //TODO class to go after click 'numOfInvated'
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