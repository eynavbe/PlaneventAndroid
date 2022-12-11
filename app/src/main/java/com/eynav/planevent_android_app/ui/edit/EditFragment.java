package com.eynav.planevent_android_app.ui.edit;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.eynav.planevent_android_app.R;

public class EditFragment extends Fragment  {

    SharedPreferences shareType;
    String typePage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        shareType = getContext().getSharedPreferences("type", MODE_PRIVATE);
        typePage = shareType.getString("type", "default if empty");
        if (typePage.equals("Hall")) {
            ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("עריכה");
            return inflater.inflate(R.layout.fragment_hall_edit, container, false);

        }else {
            ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("סיכום");
            return inflater.inflate(R.layout.fragment_client_summary, container, false);

        }

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