package com.example.electronicsstore.admin.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.electronicsstore.adapter.OrderDetailsAdapter;
import com.example.electronicsstore.database.DatabaseHelper;
import com.example.electronicsstore.databinding.FragmentOrderDetailsBinding;
import com.example.electronicsstore.model.HelperClass;
import com.example.electronicsstore.model.OrderModel;

import java.util.Objects;

public class OrderDetailsFragment extends Fragment {
    private FragmentOrderDetailsBinding binding;
    OrderDetailsAdapter orderDetailsAdapter;
    OrderModel orderModel;
    DatabaseHelper databaseHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderModel = (OrderModel) getArguments().getSerializable("data");
        }
    }

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false);
        binding.tvLabel.setText("Order Details");
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseHelper = new DatabaseHelper(requireContext());
        if (Objects.equals(HelperClass.checkUser, "User")) {
            binding.statusRadioGroup.setEnabled(false);
            for (int i = 0; i < binding.statusRadioGroup.getChildCount(); i++) {
                binding.statusRadioGroup.getChildAt(i).setEnabled(false);
            }
        }

        binding.rvOrderDetails.setLayoutManager(new LinearLayoutManager(requireContext()));
        orderDetailsAdapter = new OrderDetailsAdapter(orderModel.getProductList(), requireContext());
        binding.rvOrderDetails.setAdapter(orderDetailsAdapter);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

        binding.radioProcessing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderModel.setStatus("Processing");
                databaseHelper.updateOrder(orderModel);
            }
        });

        binding.radioAccepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderModel.setStatus("Accepted");
                databaseHelper.updateOrder(orderModel);
            }
        });

        binding.radioDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderModel.setStatus("Done");
                databaseHelper.updateOrder(orderModel);
            }
        });

        switch (orderModel.getStatus()) {
            case "Processing":
                binding.radioProcessing.setChecked(true);
                break;
            case "Accepted":
                binding.radioAccepted.setChecked(true);
                break;
            case "Done":
                binding.radioDone.setChecked(true);
                break;
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();

        binding.rvOrderDetails.setLayoutManager(new LinearLayoutManager(requireContext()));
        orderDetailsAdapter = new OrderDetailsAdapter(orderModel.getProductList(), requireContext());
        binding.rvOrderDetails.setAdapter(orderDetailsAdapter);
        orderDetailsAdapter.notifyDataSetChanged();

    }
}