package com.eynav.planevent_android_app.ui.schedule;

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
import com.eynav.planevent_android_app.databinding.FragmentScheduleBinding;

public class ScheduleFragment extends Fragment {

    SharedPreferences shareType;
    String typePage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareType = getContext().getSharedPreferences("type", MODE_PRIVATE);
        typePage = shareType.getString("type", "default if empty");
        if (typePage.equals("Hall")){
            ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("לו\"ז");
        }else {
            ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("לו\"ז");

        }
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (typePage.equals("Hall")) {
        }
        if (typePage.equals("Client")) {
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}