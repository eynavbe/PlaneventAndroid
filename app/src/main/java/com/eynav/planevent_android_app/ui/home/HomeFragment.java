package com.eynav.planevent_android_app.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.eynav.planevent_android_app.R;
import com.eynav.planevent_android_app.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    SharedPreferences shareType;
    String type;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        shareType = getContext().getSharedPreferences("type", MODE_PRIVATE);
         type = shareType.getString("type", "default if empty");
        if (type.equals("Hall")){
            ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("הבית");
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}