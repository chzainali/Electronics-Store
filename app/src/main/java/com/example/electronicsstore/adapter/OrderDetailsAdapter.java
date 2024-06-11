package com.example.electronicsstore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.electronicsstore.R;
import com.example.electronicsstore.model.ProductsModel;

import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder> {
    List<ProductsModel> list;
    Context context;

    public OrderDetailsAdapter(List<ProductsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setList(List<ProductsModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_orders_details, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ProductsModel productsModel = list.get(position);
        holder.tvProductName.setText("Name: " + productsModel.getName());
        holder.tvQuantity.setText("Quantity: " + productsModel.getQuantity());

        double originalPrice = Double.parseDouble(productsModel.getOriginalPrice());
        int quantity = Integer.parseInt(productsModel.getQuantity());
        double totalPrice = originalPrice * quantity;
        holder.tvPrice.setText("Price: " + String.format("%.2f", totalPrice));

        Glide.with(context)
                .asBitmap()
                .load(productsModel.getImageUri())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        holder.ivImage.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvQuantity, tvPrice;
        ImageView ivImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductNameOrder);
            tvQuantity = itemView.findViewById(R.id.tvProductQuantityOrder);
            tvPrice = itemView.findViewById(R.id.tvProductPriceOrder);
            ivImage = itemView.findViewById(R.id.ivImage);

        }
    }
}
