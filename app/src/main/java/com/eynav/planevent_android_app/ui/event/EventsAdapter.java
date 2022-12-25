package com.eynav.planevent_android_app.ui.event;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.eynav.planevent_android_app.Event;
import com.eynav.planevent_android_app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsAdapterHolder>{
    private List<Event> events;
    Context context;
    boolean test = true;
    String[] type = {"סוג אירוע", "חתונה", "בת מצווה",
            "בר מצווה", "ברית מילה",
            "עסקים", "אחר"};
    String[] hour = {"אירוע ערב", "אירוע צהריים"};
    String typeChoice = "סוג אירוע";
    String hourChoice = "אירוע ערב";
    String dateEvent = "";
    String emailClient1;
    long countInvited;
    long price ;
    String lastName;
    String nameUser;
    public EventsAdapter(Context context, List<Event> events, String nameUser) {
        this.context = context;
        this.events = events;
        this.nameUser = nameUser;
    }
    @NonNull
    @Override
    public EventsAdapter.EventsAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.events_card_view,parent,false);
        return new EventsAdapter.EventsAdapterHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull EventsAdapter.EventsAdapterHolder holder, @SuppressLint("RecyclerView") int position) {
        Event event = events.get(position);
        holder.tvTypeEvent.setText(event.getTypeEvent());
        holder.tvDateEvent.setText(event.getDateEvent());
        holder.tvLastNameEvent.setText(event.getLastNameEvent());
        holder.event = event;
        holder.imUpdateEvent.setOnClickListener(l ->{
            Dialog dialogUpdateEvent = new Dialog(context);
            dialogUpdateEvent.setContentView(R.layout.add_update_event);
            EditText etEmailClient1 = dialogUpdateEvent.findViewById(R.id.etEmailClient1);
            EditText  etEmailClient2 = dialogUpdateEvent.findViewById(R.id.etEmailClient2);
            Button btnAddUpdateEvent = dialogUpdateEvent.findViewById(R.id.btnAddUpdateEvent);
            Spinner spTypeEvent = dialogUpdateEvent.findViewById(R.id.spTypeEvent);
            EditText etDateEvent = dialogUpdateEvent.findViewById(R.id.etDateEvent);
            Spinner spHourEvent = dialogUpdateEvent.findViewById(R.id.spHourEvent);
            EditText etCountInvited = dialogUpdateEvent.findViewById(R.id.etCountInvited);
            EditText etPrice = dialogUpdateEvent.findViewById(R.id.etPrice);
            EditText etLastName = dialogUpdateEvent.findViewById(R.id.etLastName);
            btnAddUpdateEvent.setText("Update");
            TextView tvTitleAddUpdateEvent = dialogUpdateEvent.findViewById(R.id.tvTitleAddUpdateEvent);
            tvTitleAddUpdateEvent.setText("Update Event");
            etEmailClient1.setText(event.getEmailClient1());
            etEmailClient2.setText(event.getEmailClient2());
            etDateEvent.setText(event.getDateEvent());
            etCountInvited.setText(String.valueOf(event.getCountInvited()));
            etPrice.setText(String.valueOf(event.getPrice()));
            etLastName.setText(event.getLastNameEvent());
            emailClient1 = event.getEmailClient1();
            countInvited =event.getCountInvited();
            price = event.getPrice();
            lastName =event.getLastNameEvent();
            dateEvent = event.getDateEvent();
            typeChoice = event.getTypeEvent();
            hourChoice = event.getHourEvent();
            spTypeEvent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    typeChoice = type[position];
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            ArrayAdapter adType = new ArrayAdapter(context, android.R.layout.simple_spinner_item, type);
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

            ArrayAdapter adHour = new ArrayAdapter(context, android.R.layout.simple_spinner_item, hour);
            adHour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spHourEvent.setAdapter(adHour);
            for (int i = 0; i < type.length; i++) {
                if (type[i].equals(event.getTypeEvent())){
                    spTypeEvent.setSelection(i);
                }
            }
            for (int i = 0; i < hour.length; i++) {
                if (hour[i].equals(event.getHourEvent())){
                    spHourEvent.setSelection(i);
                }
            }
            etDateEvent.setOnClickListener( k ->{
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
                    // set day of month , month and year value in the edit text
                    dateEvent = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    etDateEvent.setText(dateEvent);
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            });
            btnAddUpdateEvent.setOnClickListener(h -> {

                saveDeleteProduct(holder.event, 0 , false);
                test = true;

                if (!etEmailClient1.getText().toString().equals("")) {
                    emailClient1 = etEmailClient1.getText().toString();
                } else {
                    Toast.makeText(context, "please enter email client 1", Toast.LENGTH_SHORT).show();
                    test = false;
                }
                if (!etCountInvited.getText().toString().equals("")) {
                    countInvited = Integer.parseInt(etCountInvited.getText().toString());
                } else {
                    Toast.makeText(context, "please enter count invited", Toast.LENGTH_SHORT).show();
                    test = false;
                }
                if (!etPrice.getText().toString().equals("")) {
                    price = Integer.parseInt(etPrice.getText().toString());
                } else {
                    Toast.makeText(context, "please enter email client 1", Toast.LENGTH_SHORT).show();
                    test = false;
                }
                if (!etLastName.getText().toString().equals("")) {
                    lastName = etLastName.getText().toString();
                } else {
                    Toast.makeText(context, "please enter email client 1", Toast.LENGTH_SHORT).show();
                    test = false;
                }


                if (dateEvent.equals("")){
                    Toast.makeText(context, "please choose date event", Toast.LENGTH_SHORT).show();
                    test = false;
                }
                if (typeChoice.equals("סוג אירוע")){
                    Toast.makeText(context, "please enter type event", Toast.LENGTH_SHORT).show();
                    test = false;
                }
                String emailClient2 = etEmailClient2.getText().toString();
                if (test){
                    Event event1 = new Event(emailClient1, emailClient2,countInvited,price,typeChoice,dateEvent,lastName,hourChoice);
                    addEventToFirebase(event1);
                    events.set(position,event1);
                    notifyItemChanged(position);
                    dialogUpdateEvent.dismiss();
                }
            });
            dialogUpdateEvent.show();
        });
        holder.imDeleteEvent.setOnClickListener(l ->{
            AlertDialog.Builder builderDelete = new AlertDialog.Builder(context)
                    .setTitle("Delete Event")
                    .setMessage("אתה בטוח שאתה רוצה למחוק את האירוע?")
                    .setIcon(R.drawable.ic_baseline_delete_24)
                    .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            saveDeleteProduct(holder.event,position, true);


                        }
                    })
                    .setNegativeButton("לא", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

            builderDelete.show();
        });
    }
    private void addEventToFirebase(Event event1) {
        System.out.println("addEventToFirebase");
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
        String date = event1.getDateEvent();
        date = date.replaceAll("/","");
        String eventname = event1.getLastNameEvent()+date;
        db.collection("hall").document(nameUser).collection("events").document(eventname)
                .set(event)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("DocumentSnapshot added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error adding document");
                    }
                });

    }
    public void saveDeleteProduct(Event event1, int position, boolean delete){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String date = event1.getDateEvent();
        date = date.replaceAll("/","");
        String eventname = event1.getLastNameEvent()+date;
        db.collection("hall").document(nameUser).collection("events").document(eventname)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (delete){
                            events.remove(position);

                            notifyItemRemoved(position);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("error",e.getMessage());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
    public class EventsAdapterHolder extends RecyclerView.ViewHolder {
        Event event;
        TextView tvTypeEvent, tvDateEvent, tvLastNameEvent;
        ImageView imUpdateEvent, imDeleteEvent;
        LinearLayout cartEvent;

        public EventsAdapterHolder(@NonNull View itemView) {
            super(itemView);
            cartEvent = itemView.findViewById(R.id.cartEvent);
            tvTypeEvent = itemView.findViewById(R.id.tvHallName);
            tvDateEvent = itemView.findViewById(R.id.tvHallArea);
            tvLastNameEvent = itemView.findViewById(R.id.tvLastNameEvent);
            imUpdateEvent = itemView.findViewById(R.id.imUpdateEvent);
            imDeleteEvent = itemView.findViewById(R.id.imDeleteEvent);
        }
    }
    }
