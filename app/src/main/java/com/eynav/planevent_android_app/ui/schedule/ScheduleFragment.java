package com.eynav.planevent_android_app.ui.schedule;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.eynav.planevent_android_app.databinding.FragmentScheduleBinding;

public class ScheduleFragment extends Fragment {

    private FragmentScheduleBinding binding;
    SharedPreferences shareType;
    String type;
    public void onStart() {
        super.onStart();
        shareType = getContext().getSharedPreferences("type", MODE_PRIVATE);
         type = shareType.getString("type", "default if empty");
        if (type.equals("Hall")){
            ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("לו\"ז");
        }else {
            ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("לו\"ז");

        }
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ScheduleViewModel dashboardViewModel =
                new ViewModelProvider(this).get(ScheduleViewModel.class);

        binding = FragmentScheduleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}