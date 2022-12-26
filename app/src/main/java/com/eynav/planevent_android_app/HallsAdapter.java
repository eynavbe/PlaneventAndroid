package com.eynav.planevent_android_app;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class HallsAdapter extends RecyclerView.Adapter<HallsAdapter.HallsAdapterHolder>{
    Context context;
    List<Hall> halls;
    String emailClient;
    boolean test = false;
    public HallsAdapter(Context context, List<Hall> events, String emailClient) {
        this.context = context;
        this.halls = events;
        this.emailClient = emailClient;
    }
    @NonNull
    @Override
    public HallsAdapter.HallsAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.choose_hall_card_view,parent,false);
        return new HallsAdapter.HallsAdapterHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HallsAdapter.HallsAdapterHolder holder, int position) {
        Hall hall = halls.get(position);
        holder.hall = hall;
        holder.tvHallName.setText(hall.getNameHall());
        holder.tvHallArea.setText(hall.getHallArea());
        String textCountPeople = "לאירוע עד " + hall.getHallCountPeople()+" איש";
        holder.tvHallCountPeople.setText(textCountPeople);

    }
    private void CheckIfCustomerRegisteredToHallFromFirebase(String hallName, View itemView, String hallNumber) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("hall").document(hallName).collection("events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String emailClient1 = String.valueOf(document.getData().get("emailClient1"));
                                String emailClient2 = String.valueOf(document.getData().get("emailClient2"));
                                if (emailClient1.equals(emailClient)){
                                    SharedPreferences shareHall = context.getSharedPreferences("hall", MODE_PRIVATE);
                                    // save your string in SharedPreferences
                                    shareHall.edit().putString("hall", hallName).commit();
                                    Intent homeHall= new Intent(itemView.getContext(), MainActivity.class);
                                    // get or create SharedPreferences
                                    SharedPreferences shareType = itemView.getContext().getSharedPreferences("type", MODE_PRIVATE);
                                    // save your string in SharedPreferences
                                    shareType.edit().putString("type", "Client").commit();
                                    itemView.getContext().startActivity(homeHall);
                                    test = true;
                                }
                                if (emailClient2.equals(emailClient)){
                                    // get or create SharedPreferences
                                    SharedPreferences shareHall = context.getSharedPreferences("hall", MODE_PRIVATE);

                                    // save your string in SharedPreferences
                                    shareHall.edit().putString("hall", hallName).commit();
                                    Intent homeHall= new Intent(itemView.getContext(), MainActivity.class);
                                    // get or create SharedPreferences
                                    SharedPreferences shareType = itemView.getContext().getSharedPreferences("type", MODE_PRIVATE);

                                    // save your string in SharedPreferences
                                    shareType.edit().putString("type", "Client").commit();
                                    itemView.getContext().startActivity(homeHall);
                                    test = true;
                                }
                            }

                            if (!test){
                                AlertDialog.Builder builderDelete = new AlertDialog.Builder(context)
                                        .setTitle("לא רשום")
                                        .setMessage("אתה לא רשום אצל האולם, אתה רוצה להתקשר לאולם?")
                                        .setIcon(R.drawable.ic_baseline_delete_24)
                                        .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                callIntent.setData(Uri.parse("tel:"+hallNumber));//change the number
                                                context.startActivity(callIntent);

                                            }
                                        }).setNegativeButton("לא", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        });

                                builderDelete.show();
                            }

                        } else {
                            AlertDialog.Builder builderDelete = new AlertDialog.Builder(context)
                                    .setTitle("לא רשום")
                                    .setMessage("אתה לא רשום אצל האולם, אתה רוצה להתקשר לאולם?")
                                    .setIcon(R.drawable.ic_baseline_delete_24)
                                    .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                                            callIntent.setData(Uri.parse("tel:"+hallNumber));//change the number
                                            context.startActivity(callIntent);

                                        }
                                    }).setNegativeButton("לא", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    });

                            builderDelete.show();
                        }
                    }}).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        AlertDialog.Builder builderDelete = new AlertDialog.Builder(context)
                                .setTitle("לא רשום")
                                .setMessage("אתה לא רשום אצל האולם, אתה רוצה להתקשר לאולם?")
                                .setIcon(R.drawable.ic_baseline_delete_24)
                                .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                                        callIntent.setData(Uri.parse("tel:"+hallNumber));//change the number
                                        context.startActivity(callIntent);

                                    }
                                }).setNegativeButton("לא", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });

                        builderDelete.show();
                    }
                });
    }
    @Override
    public int getItemCount() {
        return this.halls.size();
    }

    public class HallsAdapterHolder extends RecyclerView.ViewHolder {
        Hall hall;
        TextView tvHallName, tvHallArea, tvHallCountPeople;
        LinearLayout cartHall;

        public HallsAdapterHolder(View itemView) {
            super(itemView);
            tvHallName = itemView.findViewById(R.id.tvHallName);
            tvHallArea = itemView.findViewById(R.id.tvHallArea);
            tvHallCountPeople = itemView.findViewById(R.id.tvHallCountPeople);
            cartHall = itemView.findViewById(R.id.cartHall);
            itemView.setOnClickListener((v) ->{
                CheckIfCustomerRegisteredToHallFromFirebase(hall.getNameHall(),itemView,hall.getPhoneNum());
            });
        }
    }
}
