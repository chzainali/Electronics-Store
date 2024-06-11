package com.example.electronicsstore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.electronicsstore.R;
import com.example.electronicsstore.database.DatabaseHelper;
import com.example.electronicsstore.model.HelperClass;
import com.example.electronicsstore.model.ProductsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    List<ProductsModel> list;
    Context context;
    List<ProductsModel> listProducts = new ArrayList<>();
    String checkFrom;
    DatabaseHelper databaseHelper;

    public ProductsAdapter(List<ProductsModel> list, Context context, String checkFrom, DatabaseHelper databaseHelper) {
        this.list = list;
        this.context = context;
        this.checkFrom = checkFrom;
        this.databaseHelper = databaseHelper;
    }

    public void setList(List<ProductsModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_products, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ProductsModel productsModel = list.get(position);
        holder.tvProductName.setText(productsModel.getName());

        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        if (productsModel.getOfferPrice().isEmpty()){
            holder.clOldPrice.setVisibility(View.GONE);
            String amount = df.format(Double.parseDouble(productsModel.getOriginalPrice()));
            holder.tvPrice.setText("SAR"+amount);
        }else{
            String amount1 = df.format(Double.parseDouble(productsModel.getOfferPrice()));
            holder.tvPrice.setText("SAR"+amount1);
            String amount2 = df.format(Double.parseDouble(productsModel.getOriginalPrice()));
            holder.tvOriginalPrice.setText("SAR"+amount2);
        }
        Glide.with(context)
                .asBitmap()
                .load(Uri.parse(productsModel.getImageUri()))
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        // Now you have the Bitmap, you can set it as the image resource.
                        holder.ivImage.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // This is called when the Drawable is cleared, for example, when the view is recycled.
                    }
                });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("data", productsModel);
                if (checkFrom.contentEquals("products")){
                    if (Objects.equals(HelperClass.checkUser, "Admin")){
                        Navigation.findNavController(v).navigate(R.id.action_navigation_products_to_productDetailsFragment, bundle);
                    }else{
                        Navigation.findNavController(v).navigate(R.id.action_productsFragment_to_productDetailsFragment2, bundle);
                    }
                }else if (checkFrom.contentEquals("userHome")){
                    Navigation.findNavController(v).navigate(R.id.action_userHomeFragment_to_productDetailsFragment2, bundle);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvPrice, tvOriginalPrice;
        ConstraintLayout clOldPrice;
        ImageView ivImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clOldPrice = itemView.findViewById(R.id.clOldPrice);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvOriginalPrice = itemView.findViewById(R.id.tvOriginalPrice);
            ivImage = itemView.findViewById(R.id.ivImage);
        }
    }
}
