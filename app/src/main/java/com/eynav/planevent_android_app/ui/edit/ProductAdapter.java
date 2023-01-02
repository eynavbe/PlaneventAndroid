package com.eynav.planevent_android_app.ui.edit;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eynav.planevent_android_app.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductAdapterHolder>{

    List<Product> products;
    Context context;
    int countChoose = 0;
    int countInPrice;
    public ProductAdapter(Context context, List<Product> products, int countInPrice, int countAll) {
        this.context = context;
        this.products = products;
        this.countInPrice = countInPrice;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).isInPrice() && products.get(i).isChooseThis()){
                this.countInPrice++;
            }
        }
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
        if (product.getPriceClient() != 0){
            holder.tvClientProductPrice.setText(String.valueOf(product.getPriceClient()));
        }else {
            if (product.isInPrice()){
                holder.tvClientProductPrice.setText(String.valueOf(0));
            }else {
                holder.tvClientProductPrice.setText(String.valueOf(product.getPrice()));
            }
        }

        holder.ctvClientChooseProduct.setChecked(product.isChooseThis());

        if (product.getImage() != null){
            Glide.with(context)
                    .load(Uri.parse(product.getImage())) // the uri you got from Firebase
                    .centerCrop()
                    .override(200,200)
                    .into(holder.imImageClientProduct); //Your imageView variable
        }
        holder.product = product;

        holder.ctvClientChooseProduct.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (holder.ctvClientChooseProduct.isChecked()) {
                if (!product.isInPrice()){
                    AlertDialog.Builder builderDelete = new AlertDialog.Builder(context)
                            .setTitle("בתוספת תשלום")
                            .setMessage("מוצר זה לא כלול בתשלום, היית רוצה להוסיף עוד כסף בשביל המוצר הזה?")
                            .setIcon(R.drawable.ic_baseline_delete_24)
                            .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    product.setChooseThis(true);
//                                    holder.tvClientProductPrice.setText(String.valueOf(product.getPrice()));
                                    product.setPriceClient(product.getPrice());
                                    countChoose ++;


                                }
                            })
                            .setNegativeButton("לא", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    product.setChooseThis(false);
                                    holder.ctvClientChooseProduct.setChecked(false);
                                }
                            });

                    builderDelete.show();
                }else {

                    countChoose ++;
                    if (countChoose > countInPrice){
                        AlertDialog.Builder builderDelete = new AlertDialog.Builder(context)
                                .setTitle("בתוספת תשלום")
                                .setMessage("הגעת לסכום המרבי שכלול במחיר, היית רוצה להוסיף עוד כסף בשביל המוצר הזה?")
                                .setIcon(R.drawable.ic_baseline_delete_24)
                                .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        holder.tvClientProductPrice.setText(String.valueOf(product.getPrice()));
                                        product.setPriceClient(product.getPrice());
                                        product.setChooseThis(true);
                                    }
                                })
                                .setNegativeButton("לא", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        holder.ctvClientChooseProduct.setChecked(false);
                                        product.setChooseThis(false);
                                        countChoose --;
                                    }
                                });

                        builderDelete.show();
                    }

                }

            } else {
                System.out.println(product);
                product.setChooseThis(false);
                System.out.println(Integer.parseInt(holder.tvClientProductPrice.getText().toString()));
                product.setPriceClient(0L);

                if (Integer.parseInt(holder.tvClientProductPrice.getText().toString())> 0){
//                    holder.tvClientProductPrice.setText(String.valueOf(product.getPrice()));
                    if (product.getPriceClient() != 0){
                        holder.tvClientProductPrice.setText(String.valueOf(product.getPriceClient()));
                    }else {
                        if (product.isInPrice()){
                            holder.tvClientProductPrice.setText(String.valueOf(0));
                        }else {
                            holder.tvClientProductPrice.setText(String.valueOf(product.getPrice()));
                        }
                    }
                }
                System.out.println("false");
                countChoose --;
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
        ImageView imImageClientProduct;
        public ProductAdapterHolder(View itemView) {
            super(itemView);
            tvClientProductName = itemView.findViewById(R.id.tvClientProductName);
            tvClientProductPrice = itemView.findViewById(R.id.tvClientProductPrice);
            ctvClientChooseProduct = itemView.findViewById(R.id.ctvClientChooseProduct);
            imImageClientProduct = itemView.findViewById(R.id.imImageClientProduct);
        }
    }
}
