package com.eynav.planevent_android_app;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class HallsAdapter extends RecyclerView.Adapter<HallsAdapter.HallsAdapterHolder>{
    Context context;
    List<Hall> halls;
    String emailClient;
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
    private void CheckIfCustomerRegisteredToHallFromFirebase(String hallName, View itemView) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("hall").document(hallName).collection("events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getId());
                                String emailClient1 = String.valueOf(document.getData().get("emailClient1"));
                                String emailClient2 = String.valueOf(document.getData().get("emailClient2"));
                                if (emailClient1.equals(emailClient)){
                                    // get or create SharedPreferences
                                    SharedPreferences shareType = context.getSharedPreferences("type", MODE_PRIVATE);

                                    // save your string in SharedPreferences
                                    shareType.edit().putString("hall", hallName).commit();
                                    Intent homeHall= new Intent(itemView.getContext(), MainActivity.class);
                                    // get or create SharedPreferences
                                    shareType = itemView.getContext().getSharedPreferences("type", MODE_PRIVATE);

                                    // save your string in SharedPreferences
                                    shareType.edit().putString("type", "Client").commit();
                                    itemView.getContext().startActivity(homeHall);
                                }
                                if (emailClient2.equals(emailClient)){
                                    // get or create SharedPreferences
                                    SharedPreferences shareType = context.getSharedPreferences("type", MODE_PRIVATE);

                                    // save your string in SharedPreferences
                                    shareType.edit().putString("hall", hallName).commit();
                                    Intent homeHall= new Intent(itemView.getContext(), MainActivity.class);
                                    // get or create SharedPreferences
                                    shareType = itemView.getContext().getSharedPreferences("type", MODE_PRIVATE);

                                    // save your string in SharedPreferences
                                    shareType.edit().putString("type", "Client").commit();
                                    itemView.getContext().startActivity(homeHall);
                                }
                            }

                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
                            System.out.println("Error getting documents.");
                            System.out.println(task.getException().toString());
                        }
                    }});
    }
    @Override
    public int getItemCount() {
        return this.halls.size();
    }

    public class HallsAdapterHolder extends RecyclerView.ViewHolder {
        Hall hall;
        ImageView imHallImage;
        TextView tvHallName, tvHallArea, tvHallCountPeople;
        LinearLayout cartHall;

        public HallsAdapterHolder(View itemView) {
            super(itemView);
            imHallImage = itemView.findViewById(R.id.imHallImage);
            tvHallName = itemView.findViewById(R.id.tvHallName);
            tvHallArea = itemView.findViewById(R.id.tvHallArea);
            tvHallCountPeople = itemView.findViewById(R.id.tvHallCountPeople);
            cartHall = itemView.findViewById(R.id.cartHall);
            itemView.setOnClickListener((v) ->{
                CheckIfCustomerRegisteredToHallFromFirebase(hall.getNameHall(),itemView);


            });
        }
    }
}
