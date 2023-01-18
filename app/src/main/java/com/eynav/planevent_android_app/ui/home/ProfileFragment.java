package com.eynav.planevent_android_app.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eynav.planevent_android_app.CloudFunctions;
import com.eynav.planevent_android_app.Hall;
import com.eynav.planevent_android_app.HallsAdapter;
import com.eynav.planevent_android_app.MainActivity;
import com.eynav.planevent_android_app.MainActivity2;
import com.eynav.planevent_android_app.R;
import com.eynav.planevent_android_app.RegisterHallActivity;
import com.eynav.planevent_android_app.SignupHallActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctionsException;

import java.util.List;


public class ProfileFragment extends Fragment {
    EditText idAreaHall, idNumberInvtManag, idManagePhoneNum;
    TextView editName, editEmail;
    Button saveButton;

    String nameUser, emailUser,areaHall, numberInvtManag, managePhoneNum;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editName = view.findViewById(R.id.editName);
        idAreaHall = view.findViewById(R.id.idAreaHall);
        idNumberInvtManag = view.findViewById(R.id.idNumberInvtManag);
        idManagePhoneNum = view.findViewById(R.id.idManagePhoneNum);
        editEmail = view.findViewById(R.id.editEmail);

        saveButton = view.findViewById(R.id.saveButton);

        showData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAreaHallChanged() ||  isNumberInvtManagChanged() || isManagePhoneNumChanged()){
                    new CloudFunctions().writeHallToFirebase("hall",  nameUser,  nameUser, areaHall, numberInvtManag, managePhoneNum, emailUser)
                            .addOnCompleteListener(new OnCompleteListener<Hall>() {
                                @Override
                                public void onComplete(@NonNull Task<Hall> task) {
                                    System.out.println(task.toString());
                                    System.out.println(task.getResult().toString());
                                    if (task.isSuccessful()) {
                                        Log.e("demo",""+task.getResult());
                                        Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();

                                    }else{
                                        Exception e = task.getException();
                                        if (e instanceof FirebaseFunctionsException) {
                                            FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                                            FirebaseFunctionsException.Code code = ffe.getCode();
                                            Object details = ffe.getDetails();
                                            System.out.println(details.toString());
                                        }
                                    }
                                }
                            });

                } else {
                    Toast.makeText(getContext(), "No Changes Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
//
//    private boolean isNameChanged() {
//        if (!nameUser.equals(editName.getText().toString())){
//            nameUser = editName.getText().toString();
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    private boolean isEmailChanged() {
//        if (!emailUser.equals(editEmail.getText().toString())){
//            emailUser = editEmail.getText().toString();
//            return true;
//        } else {
//            return false;
//        }
//    }
    private boolean isAreaHallChanged() {
        if (!areaHall.equals(idAreaHall.getText().toString())){
            areaHall = idAreaHall.getText().toString();
            return true;
        } else {
            return false;
        }
    }
    private boolean isNumberInvtManagChanged() {
        if (!numberInvtManag.equals(idNumberInvtManag.getText().toString())){
            numberInvtManag = idNumberInvtManag.getText().toString();
            return true;
        } else {
            return false;
        }
    }
    private boolean isManagePhoneNumChanged() {
        if (!managePhoneNum.equals(idManagePhoneNum.getText().toString())){
            managePhoneNum = idManagePhoneNum.getText().toString();
            return true;
        } else {
            return false;
        }
    }


    public void showData(){
        SharedPreferences shareName;
        shareName = getContext().getSharedPreferences("name", MODE_PRIVATE);
        String nameHall = shareName.getString("name", "default if empty");
        (new CloudFunctions()).readHallsDataFromFirebase("hall")
                .addOnCompleteListener(new OnCompleteListener<List<Hall>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Hall>> task) {
                        System.out.println(task.toString());
                        System.out.println(task.getResult().toString());
                        if (task.isSuccessful()) {
                            for (int i = 0; i < task.getResult().size(); i++) {
                                System.out.println(task.getResult().get(i));
                                if (task.getResult().get(i).getNameHall().equals(nameHall)){
                                    nameUser = task.getResult().get(i).getNameHall();
                                    emailUser = task.getResult().get(i).getEmail();
                                    areaHall=  task.getResult().get(i).getHallArea();
                                    numberInvtManag=  task.getResult().get(i).getHallCountPeople();
                                    managePhoneNum =task.getResult().get(i).getPhoneNum();
                                    editName.setText(nameUser);
                                    editEmail.setText(emailUser);
                                    idAreaHall.setText(areaHall);
                                    idNumberInvtManag.setText(numberInvtManag);
                                    idManagePhoneNum.setText(managePhoneNum);
                                }
                            }
                        }else{
                            Exception e = task.getException();
                            if (e instanceof FirebaseFunctionsException) {
                                FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                                FirebaseFunctionsException.Code code = ffe.getCode();
                                Object details = ffe.getDetails();
                                System.out.println(details.toString());
                            }
                        }
                    }
                });


//        Intent intent = getIntent();
//        nameUser = intent.getStringExtra("name");
//        emailUser = intent.getStringExtra("email");
//        usernameUser = intent.getStringExtra("username");
//        passwordUser = intent.getStringExtra("password");
//        String nameUser, emailUser,areaHall, numberInvtManag, managePhoneNum;


    }
}