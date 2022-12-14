package com.eynav.planevent_android_app.ui.edit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eynav.planevent_android_app.Event;
import com.eynav.planevent_android_app.R;
import com.eynav.planevent_android_app.ui.event.EventsAdapter;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductAdapterHolder>{

    List<Product> products;
    Context context;


    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }
    @NonNull
    @Override
    public ProductAdapter.ProductAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.client_choose_card_view,parent,false);
        return new ProductAdapter.ProductAdapterHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductAdapterHolder holder, int position) {
        Product product = products.get(position);
        holder.tvClientProductName.setText(product.getName());
        holder.tvClientProductPrice.setText(String.valueOf(product.getPrice()));

        holder.product = product;

        holder.ctvClientChooseProduct.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (holder.ctvClientChooseProduct.isChecked()) {
                product.setChooseThis(true);
                System.out.println("true");
            } else {
                product.setChooseThis(false);
                System.out.println("false");
            }
        });
    }
    public List<Product>  getData(){
        return this.products;
    }
    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductAdapterHolder extends RecyclerView.ViewHolder {
        Product product;
        TextView tvClientProductName, tvClientProductPrice;
        CheckBox ctvClientChooseProduct;
        public ProductAdapterHolder(View itemView) {
            super(itemView);
            tvClientProductName = itemView.findViewById(R.id.tvClientProductName);
            tvClientProductPrice = itemView.findViewById(R.id.tvClientProductPrice);
            ctvClientChooseProduct = itemView.findViewById(R.id.ctvClientChooseProduct);
        }
    }
}
