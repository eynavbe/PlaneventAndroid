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

import java.util.List;

public class HallsAdapter extends RecyclerView.Adapter<HallsAdapter.HallsAdapterHolder>{
    Context context;
    List<Hall> halls;
    public HallsAdapter(Context context, List<Hall> events) {
        this.context = context;
        this.halls = events;
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
        holder.tvHallArea.setText(hall.getTvHallArea());
        String textCountPeople = "לאירוע עד " + hall.getTvHallCountPeople()+" איש";
        holder.tvHallCountPeople.setText(textCountPeople);

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
                // get or create SharedPreferences
                SharedPreferences shareType = context.getSharedPreferences("type", MODE_PRIVATE);

                // save your string in SharedPreferences
                shareType.edit().putString("hall", hall.getNameHall()).commit();





                Intent homeHall= new Intent(itemView.getContext(), MainActivity.class);
                // get or create SharedPreferences
                shareType = itemView.getContext().getSharedPreferences("type", MODE_PRIVATE);

                // save your string in SharedPreferences
                shareType.edit().putString("type", "Client").commit();
                itemView.getContext().startActivity(homeHall);



//                Bundle bundle = new Bundle();
//                bundle.putParcelable(TourInfo.EXTRA_TOUR, tour);
//                Fragment fragment = new TourInfo();
//                fragment.setArguments(bundle);
//                AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).addToBackStack(null).commit();
            });
        }
    }
}
