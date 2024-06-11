package com.example.electronicsstore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.electronicsstore.R;
import com.example.electronicsstore.model.HelperClass;
import com.example.electronicsstore.model.OrderModel;

import java.util.List;
import java.util.Objects;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    List<OrderModel> list;
    Context context;

    public OrderAdapter(List<OrderModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setList(List<OrderModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_orders, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        OrderModel ordersModel = list.get(position);
        holder.tvCustomerName.setText(ordersModel.getUserName());
        holder.tvOrderNumber.setText(ordersModel.getOrderId());
        holder.tvStatus.setText(ordersModel.getStatus());
        holder.tvDateTime.setText(ordersModel.getDateTime());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", ordersModel);
                if (Objects.equals(HelperClass.checkUser, "Admin")){
                    Navigation.findNavController(v).navigate(R.id.action_navigation_orders_to_orderDetailsFragment, bundle);
                }else{
                    Navigation.findNavController(v).navigate(R.id.action_ordersFragment_to_orderDetailsFragment2, bundle);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCustomerName, tvOrderNumber, tvStatus, tvDateTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCustomerName = itemView.findViewById(R.id.tvCustomerName1);
            tvOrderNumber = itemView.findViewById(R.id.tvOrderNumber1);
            tvStatus = itemView.findViewById(R.id.tvStatus1);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
        }
    }
}
