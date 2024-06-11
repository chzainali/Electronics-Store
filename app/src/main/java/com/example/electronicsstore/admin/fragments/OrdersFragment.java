package com.example.electronicsstore.admin.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.electronicsstore.R;
import com.example.electronicsstore.adapter.OrderAdapter;
import com.example.electronicsstore.database.DatabaseHelper;
import com.example.electronicsstore.databinding.FragmentOrdersBinding;
import com.example.electronicsstore.model.HelperClass;
import com.example.electronicsstore.model.OrderDetailsModel;
import com.example.electronicsstore.model.OrderModel;
import com.example.electronicsstore.model.ProductsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class OrdersFragment extends Fragment {
    private FragmentOrdersBinding binding;
    OrderAdapter orderAdapter;
    ArrayList<OrderModel> list = new ArrayList<>();
    DatabaseHelper databaseHelper;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentOrdersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        databaseHelper = new DatabaseHelper(requireContext());
        binding.rvOrders.setLayoutManager(new LinearLayoutManager(requireContext()));
        orderAdapter = new OrderAdapter(list, requireContext());
        binding.rvOrders.setAdapter(orderAdapter);

        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    orderAdapter.setList(list);
                } else {
                    filter(newText);
                }
                return false;
            }
        });

        return root;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();

        list.clear();
        if (Objects.equals(HelperClass.checkUser, "Admin")){
            list = databaseHelper.getAllOrders();
        }else{
            list = databaseHelper.getOrdersByUserId(HelperClass.users.getId());
        }
        if (list.isEmpty()){
            binding.noDataFound.setVisibility(View.VISIBLE);
            binding.rvOrders.setVisibility(View.GONE);
            binding.searchBar.setVisibility(View.GONE);
        }else{
            binding.noDataFound.setVisibility(View.GONE);
            binding.rvOrders.setVisibility(View.VISIBLE);
            binding.searchBar.setVisibility(View.VISIBLE);
        }
        binding.rvOrders.setLayoutManager(new LinearLayoutManager(requireContext()));
        orderAdapter = new OrderAdapter(list, requireContext());
        binding.rvOrders.setAdapter(orderAdapter);
        orderAdapter.notifyDataSetChanged();
    }

    public void filter(String text) {
        List<OrderModel> filteredList = new ArrayList<>();
        for (OrderModel model : list) {
            if (model.getUserName().toLowerCase(Locale.ROOT).contains(text.toLowerCase())) {
                filteredList.add(model);
            }
        }
        orderAdapter.setList(filteredList);
    }

}