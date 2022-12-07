package com.eynav.planevent_android_app.ui.edit;

import static android.content.Context.MODE_PRIVATE;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.eynav.planevent_android_app.MainActivity;
import com.eynav.planevent_android_app.databinding.FragmentEditBinding;

public class EditFragment extends Fragment  {

    private FragmentEditBinding binding;
    SharedPreferences shareType;
    String type;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EditViewModel notificationsViewModel =
                new ViewModelProvider(this).get(EditViewModel.class);
        binding = FragmentEditBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        shareType = getContext().getSharedPreferences("type", MODE_PRIVATE);
         type = shareType.getString("type", "default if empty");
        if (type.equals("Hall")) {
            ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("עריכה");
        }else {
            ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("סיכום");

        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}