package com.eynav.planevent_android_app.ui.event;


import static android.content.Context.MODE_PRIVATE;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eynav.planevent_android_app.Event;
import com.eynav.planevent_android_app.MainActivity;
import com.eynav.planevent_android_app.R;
import com.eynav.planevent_android_app.databinding.FragmentEventBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EventFragment extends Fragment {

    private FragmentEventBinding binding;
    RecyclerView rvEvent;
    FloatingActionButton fabAddEvent;
    List<Event> events = new ArrayList<>();
    EventsAdapter eventsAdapter;
    String dateEvent = "";
    boolean test = true;
    SharedPreferences shareType;

    String[] type = {"סוג אירוע", "חתונה", "בת מצווה",
            "בר מצווה", "ברית מילה",
            "עסקים", "אחר"};
    String[] hour = {"אירוע ערב", "אירוע צהריים"};
    String typeChoice = "סוג אירוע";
    String hourChoice = "אירוע ערב";
    String typePage;
    @Override
    public void onStart() {
        super.onStart();
        shareType = getContext().getSharedPreferences("type", MODE_PRIVATE);
        typePage = shareType.getString("type", "default if empty");
        if (typePage.equals("Hall")){
            ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("אירועים");
        }else {
            ((AppCompatActivity) getContext()).getSupportActionBar().setTitle("בחירות");

        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EventViewModel eventViewModel =
                new ViewModelProvider(this).get(EventViewModel.class);

        binding = FragmentEventBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rvEvent = root.findViewById(R.id.rvEvent);
        fabAddEvent = root.findViewById(R.id.fabAddEvent);
        fabAddEvent.setOnClickListener(l ->{
            Dialog dialogAddEvent = new Dialog(getContext());
            dialogAddEvent.setContentView(R.layout.add_update_event);
            EditText etEmailClient1 = dialogAddEvent.findViewById(R.id.etEmailClient1);
            EditText  etEmailClient2 = dialogAddEvent.findViewById(R.id.etEmailClient2);
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

            etDateEvent.setOnClickListener( k ->{
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
                String lastName ="";
                if (!etEmailClient1.getText().toString().equals("")) {
                    emailClient1 = etEmailClient1.getText().toString();
                } else {
                    Toast.makeText(getContext(), "please enter email client 1", Toast.LENGTH_SHORT).show();
                    test = false;
                }
                if (!etCountInvited.getText().toString().equals("")) {
                    countInvited = Integer.parseInt(etCountInvited.getText().toString());
                } else {
                    Toast.makeText(getContext(), "please enter count invited", Toast.LENGTH_SHORT).show();
                    test = false;
                }
                if (!etPrice.getText().toString().equals("")) {
                    price = Integer.parseInt(etPrice.getText().toString());
                } else {
                    Toast.makeText(getContext(), "please enter email client 1", Toast.LENGTH_SHORT).show();
                    test = false;
                }
                if (!etLastName.getText().toString().equals("")) {
                    lastName = etLastName.getText().toString();
                } else {
                    Toast.makeText(getContext(), "please enter email client 1", Toast.LENGTH_SHORT).show();
                    test = false;
                }


                if (dateEvent.equals("")){
                    Toast.makeText(getContext(), "please choose date event", Toast.LENGTH_SHORT).show();
                    test = false;
                }
                if (typeChoice.equals("סוג אירוע")){
                    Toast.makeText(getContext(), "please enter type event", Toast.LENGTH_SHORT).show();

                    test = false;
                }
                String emailClient2 = etEmailClient2.getText().toString();
                if (test){
                    Event event1 = new Event(emailClient1, emailClient2,countInvited,price,typeChoice,dateEvent,lastName,hourChoice);
                    events.add(event1);
                    eventsAdapter.notifyItemInserted(events.size()-1);
                    rvEvent.scrollToPosition(events.size()-1);
                    dialogAddEvent.dismiss();
                }

            });
            dialogAddEvent.show();
        });

        rvEvent.setLayoutManager(new LinearLayoutManager(getContext()));
        eventsAdapter = new EventsAdapter(getContext(),events);
        rvEvent.setAdapter(eventsAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}