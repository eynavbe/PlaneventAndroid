package com.eynav.planevent_android_app.ui.event;


import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eynav.planevent_android_app.CloudFunctions;
import com.eynav.planevent_android_app.Event;
import com.eynav.planevent_android_app.Hall;
import com.eynav.planevent_android_app.Loading;
import com.eynav.planevent_android_app.MainActivity;
import com.eynav.planevent_android_app.R;
import com.eynav.planevent_android_app.RegisterHallActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctionsException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventFragment extends Fragment {

    RecyclerView rvEvent;
    FloatingActionButton fabAddEvent;
    List<Event> events = new ArrayList<>();
    EventsAdapter eventsAdapter;
    String dateEvent = "";
    boolean test = true;
    SharedPreferences shareType;
    SharedPreferences shareName;
    String[] type = {"סוג אירוע", "חתונה", "בת מצווה",
            "בר מצווה", "ברית מילה",
            "עסקים", "אחר"};
    String[] hour = {"אירוע ערב", "אירוע צהריים"};
    String typeChoice = "סוג אירוע";
    String hourChoice = "אירוע ערב";
    String typePage;
    String nameUser;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        shareType = getContext().getSharedPreferences("type", MODE_PRIVATE);
        typePage = shareType.getString("type", "default if empty");

        shareName = getContext().getSharedPreferences("name", MODE_PRIVATE);
        nameUser = shareName.getString("name", "default if empty");
        if (typePage.equals("Hall")){
            ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("אירועים");
            return inflater.inflate(R.layout.fragment_hall_event, container, false);

        }else {
            ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("סיכום");
            return inflater.inflate(R.layout.fragment_client_summary, container, false);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (eventsAdapter != null){
            eventsAdapter.clear();

        }
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        if (typePage.equals("Hall")) {
            rvEvent = root.findViewById(R.id.rvEvent);
            fabAddEvent = root.findViewById(R.id.fabAddEvent);
            readEventsFromFirebase();
            fabAddEvent.setOnClickListener(l -> {
                Dialog dialogAddEvent = new Dialog(getContext());
                dialogAddEvent.setContentView(R.layout.add_update_event);
                EditText etEmailClient1 = dialogAddEvent.findViewById(R.id.etEmailClient1);
                EditText etEmailClient2 = dialogAddEvent.findViewById(R.id.etEmailClient2);
                Button btnAddUpdateEvent = dialogAddEvent.findViewById(R.id.btnAddUpdateEvent);
                Spinner spTypeEvent = dialogAddEvent.findViewById(R.id.spTypeEvent);
                EditText etDateEvent = dialogAddEvent.findViewById(R.id.etDateEvent);
                Spinner spHourEvent = dialogAddEvent.findViewById(R.id.spHourEvent);
                EditText etCountInvited = dialogAddEvent.findViewById(R.id.etCountInvited);
                EditText etPrice = dialogAddEvent.findViewById(R.id.etPrice);
                EditText etLastName = dialogAddEvent.findViewById(R.id.etLastName);
                spTypeEvent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        typeChoice = type[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                ArrayAdapter adType = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, type);
                adType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spTypeEvent.setAdapter(adType);

                spHourEvent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        hourChoice = hour[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                ArrayAdapter adHour = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, hour);
                adHour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spHourEvent.setAdapter(adHour);

                etDateEvent.setOnClickListener(k -> {
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                    // date picker dialog
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year, monthOfYear, dayOfMonth) -> {
                        // set day of month , month and year value in the edit text
                        dateEvent = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        etDateEvent.setText(dateEvent);
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                });
                btnAddUpdateEvent.setOnClickListener(h -> {
                    test = true;
                    String emailClient1 = "";
                    int countInvited = 0;
                    int price = 0;
                    String lastName = "";
                    if (typeChoice.equals("סוג אירוע") && test) {
                        Toast.makeText(getContext(), "please enter type event", Toast.LENGTH_SHORT).show();

                        test = false;
                    }
                    if (dateEvent.equals("") && test) {
                        Toast.makeText(getContext(), "please choose date event", Toast.LENGTH_SHORT).show();
                        test = false;
                    }
                    if (!etCountInvited.getText().toString().equals("")) {
                        countInvited = Integer.parseInt(etCountInvited.getText().toString());
                    } else {
                        if (test){
                            Toast.makeText(getContext(), "please enter count invited", Toast.LENGTH_SHORT).show();
                            test = false;
                        }

                    }
                    if (!etPrice.getText().toString().equals("")) {
                        price = Integer.parseInt(etPrice.getText().toString());
                    } else {
                        if (test) {
                            Toast.makeText(getContext(), "please enter price", Toast.LENGTH_SHORT).show();
                            test = false;
                        }
                    }
                    if (!etLastName.getText().toString().equals("")) {
                        lastName = etLastName.getText().toString();
                    } else {
                        if (test) {

                            Toast.makeText(getContext(), "please enter last name", Toast.LENGTH_SHORT).show();
                            test = false;
                        }
                    }
                    if (!etEmailClient1.getText().toString().equals("")) {
                        emailClient1 = etEmailClient1.getText().toString();
                    } else {
                        if (test) {
                            Toast.makeText(getContext(), "please enter email client 1", Toast.LENGTH_SHORT).show();
                            test = false;
                        }
                    }
                    String emailClient2 = etEmailClient2.getText().toString();
                    if (test) {
                        Event event1 = new Event(emailClient1, emailClient2, countInvited, price, typeChoice, dateEvent, lastName, hourChoice);
                        addEventToFirebase(event1);
                        dialogAddEvent.dismiss();
                    }

                });
                dialogAddEvent.show();
            });

            rvEvent.setLayoutManager(new LinearLayoutManager(getContext()));
            eventsAdapter = new EventsAdapter(getContext(), events, nameUser);
            rvEvent.setAdapter(eventsAdapter);
        }

        if (typePage.equals("Client")) {
            SharedPreferences shareName = getContext().getSharedPreferences("emailClient", MODE_PRIVATE);
            String emailClient = shareName.getString("emailClient", "default if empty");
            FirebaseFirestore db = FirebaseFirestore.getInstance();





            new CloudFunctions().readEventsDataFromFirebase("hall", nameUser, "events")
                    .addOnCompleteListener(new OnCompleteListener<List<Event>>() {
                        @Override
                        public void onComplete(@NonNull Task<List<Event>> task) {
                            System.out.println(task.toString());
                            System.out.println(task.getResult().toString());
                            if (task.isSuccessful()) {
                                for (int i = 0; i < task.getResult().size(); i++) {
                                    System.out.println(task.getResult().get(i));
                                    if (task.getResult().get(i).getEmailClient1().equals(emailClient)){
                                        Event event = new Event(task.getResult().get(i).getEmailClient1(),task.getResult().get(i).getEmailClient2(),task.getResult().get(i).getCountInvited(),task.getResult().get(i).getPrice(),task.getResult().get(i).getTypeEvent(),task.getResult().get(i).getDateEvent(),task.getResult().get(i).getLastNameEvent(),task.getResult().get(i).getHourEvent());
                                        Fragment myFragment = new AccountSummary(event,"client");
                                        AppCompatActivity activity = (AppCompatActivity) getContext();

                                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();

                                    }
                                    if (task.getResult().get(i).getEmailClient2().equals(emailClient)){
                                        Event event = new Event(task.getResult().get(i).getEmailClient1(),task.getResult().get(i).getEmailClient2(),task.getResult().get(i).getCountInvited(),task.getResult().get(i).getPrice(),task.getResult().get(i).getTypeEvent(),task.getResult().get(i).getDateEvent(),task.getResult().get(i).getLastNameEvent(),task.getResult().get(i).getHourEvent());
                                        Fragment myFragment = new AccountSummary(event,"client");
                                        AppCompatActivity activity = (AppCompatActivity) getContext();

                                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();

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









//            db.collection("hall").document(nameUser).collection("events").whereEqualTo("emailClient1",emailClient)
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    String emailClient1 = String.valueOf(document.getData().get("emailClient1"));
//                                    String emailClient2 = String.valueOf(document.getData().get("emailClient2"));
//                                    Long countInvited = (Long)(document.getData().get("countInvited"));
//                                    Long price = (Long)(document.getData().get("price"));
//                                    String typeEvent = String.valueOf(document.getData().get("typeEvent"));
//                                    String dateEvent = String.valueOf(document.getData().get("dateEvent"));
//                                    String lastNameEvent = String.valueOf(document.getData().get("lastNameEvent"));
//                                    String hourEvent = String.valueOf(document.getData().get("hourEvent"));
//                                    Event event = new Event(emailClient1,emailClient2,countInvited,price,typeEvent,dateEvent,lastNameEvent,hourEvent);
//                                    Fragment myFragment = new AccountSummary(event,"client");
//                                    AppCompatActivity activity = (AppCompatActivity) getContext();
//
//                                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, myFragment).addToBackStack(null).commit();
//
//                                }
//                            } else {
//                                System.out.println("Error getting documents.");
//                            }
//                        }});

        }
        }

    private void readEventsFromFirebase() {




        new CloudFunctions().readEventsDataFromFirebase("hall", nameUser, "events")
                .addOnCompleteListener(new OnCompleteListener<List<Event>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Event>> task) {
                        System.out.println(task.toString());
                        System.out.println(task.getResult().toString());
                        if (task.isSuccessful()) {
                            for (int i = 0; i < task.getResult().size(); i++) {
                                System.out.println(task.getResult().get(i));
                                Event event = new Event(task.getResult().get(i).getEmailClient1(),task.getResult().get(i).getEmailClient2(),task.getResult().get(i).getCountInvited(),task.getResult().get(i).getPrice(),task.getResult().get(i).getTypeEvent(),task.getResult().get(i).getDateEvent(),task.getResult().get(i).getLastNameEvent(),task.getResult().get(i).getHourEvent());
                                events.add(event);

                            }
                            rvEvent.setLayoutManager(new LinearLayoutManager(getContext()));
                            eventsAdapter = new EventsAdapter(getContext(), events,nameUser);
                            rvEvent.setAdapter(eventsAdapter);

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











//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("hall").document(nameUser).collection("events")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                String emailClient1 = String.valueOf(document.getData().get("emailClient1"));
//                                String emailClient2 = String.valueOf(document.getData().get("emailClient2"));
//                                Long countInvited = (Long)(document.getData().get("countInvited"));
//                                Long price = (Long)(document.getData().get("price"));
//                                String typeEvent = String.valueOf(document.getData().get("typeEvent"));
//                                String dateEvent = String.valueOf(document.getData().get("dateEvent"));
//                                String lastNameEvent = String.valueOf(document.getData().get("lastNameEvent"));
//                                String hourEvent = String.valueOf(document.getData().get("hourEvent"));
//                                Event event = new Event(emailClient1,emailClient2,countInvited,price,typeEvent,dateEvent,lastNameEvent,hourEvent);
//                                events.add(event);
//                            }
//                            rvEvent.setLayoutManager(new LinearLayoutManager(getContext()));
//                            eventsAdapter = new EventsAdapter(getContext(), events,nameUser);
//                            rvEvent.setAdapter(eventsAdapter);
//                        } else {
//                            System.out.println("Error getting documents.");
//                        }
//                    }});
    }

    private void addEventToFirebase(Event event1) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> event = new HashMap<>();
        event.put("emailClient1", event1.getEmailClient1());
        event.put("emailClient2", event1.getEmailClient2());
        event.put("countInvited", event1.getCountInvited());
        event.put("price", event1.getPrice());
        event.put("typeEvent", event1.getTypeEvent());
        event.put("dateEvent", event1.getDateEvent());
        event.put("lastNameEvent", event1.getLastNameEvent());
        event.put("hourEvent", event1.getHourEvent());
        Activity activity = getActivity();
        Loading loadingdialog = new Loading(activity);
        loadingdialog.startLoadingdialog();
        String date = event1.getDateEvent();
        date = date.replaceAll("/","");
        String eventname = event1.getLastNameEvent()+date;


        events.add(event1);




        new CloudFunctions().writeEventToFirebase("hall",  nameUser, "events", eventname, event1.getEmailClient1(), event1.getEmailClient2(), event1.getCountInvited(), event1.getPrice(), event1.getTypeEvent(), event1.getDateEvent(), event1.getLastNameEvent(), event1.getHourEvent())
                .addOnCompleteListener(new OnCompleteListener<Event>() {
                    @Override
                    public void onComplete(@NonNull Task<Event> task) {
                        System.out.println(task.toString());
                        System.out.println(task.getResult().toString());
                        if (task.isSuccessful()) {
                            rvEvent.setLayoutManager(new LinearLayoutManager(getContext()));
                            eventsAdapter = new EventsAdapter(getContext(), events,nameUser);
                            rvEvent.setAdapter(eventsAdapter);
                            loadingdialog.dismissdialog();
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








//        db.collection("hall").document(nameUser).collection("events").document(eventname)
//                .set(event)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        rvEvent.setLayoutManager(new LinearLayoutManager(getContext()));
//                        eventsAdapter = new EventsAdapter(getContext(), events,nameUser);
//                        rvEvent.setAdapter(eventsAdapter);
//                        loadingdialog.dismissdialog();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        System.out.println("Error adding document");
//                    }
//                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}