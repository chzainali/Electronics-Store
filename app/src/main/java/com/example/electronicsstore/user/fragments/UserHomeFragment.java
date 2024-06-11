package com.example.electronicsstore.user.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.electronicsstore.R;
import com.example.electronicsstore.adapter.CategoriesAdapter;
import com.example.electronicsstore.adapter.HomeCategoriesAdapter;
import com.example.electronicsstore.adapter.ProductsAdapter;
import com.example.electronicsstore.database.DatabaseHelper;
import com.example.electronicsstore.databinding.FragmentUserHomeBinding;
import com.example.electronicsstore.model.CategoryModel;
import com.example.electronicsstore.model.HelperClass;
import com.example.electronicsstore.model.ProductsModel;

import java.util.ArrayList;
import java.util.List;

public class UserHomeFragment extends Fragment {
    FragmentUserHomeBinding binding;
    HomeCategoriesAdapter categoriesAdapter;
    ProductsAdapter productsAdapter;
    DatabaseHelper databaseHelper;
    List<CategoryModel> list = new ArrayList<>();
    List<ProductsModel> listProducts = new ArrayList<>();

    public UserHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseHelper = new DatabaseHelper(requireContext());
        binding.tvWelcome.setText("Welcome\n"+ HelperClass.users.getUserName());

    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();

        // Clear existing lists and fetch categories and products with offers
        list.clear();
        listProducts.clear();
        list.addAll(databaseHelper.getAllCategories());
        listProducts.addAll(databaseHelper.getAllProductsWithOffer());

        if (listProducts.size() > 0) {
            binding.noDataFoundDeals.setVisibility(View.GONE);
            binding.rvProducts.setVisibility(View.VISIBLE);
            binding.rvProducts.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
            productsAdapter = new ProductsAdapter(listProducts, requireContext(), "userHome", databaseHelper);
            binding.rvProducts.setAdapter(productsAdapter);
            productsAdapter.notifyDataSetChanged();
        }else{
            binding.noDataFoundDeals.setVisibility(View.VISIBLE);
            binding.rvProducts.setVisibility(View.GONE);
        }

        if (list.size() > 0) {
            binding.rvCategory.setVisibility(View.VISIBLE);
            binding.noDataFoundCategory.setVisibility(View.GONE);
            binding.rvCategory.setLayoutManager(new GridLayoutManager(requireContext(), 2));
            categoriesAdapter = new HomeCategoriesAdapter(list, requireContext(), databaseHelper);
            binding.rvCategory.setAdapter(categoriesAdapter);
            categoriesAdapter.notifyDataSetChanged();
        }else{
            binding.noDataFoundCategory.setVisibility(View.VISIBLE);
            binding.rvCategory.setVisibility(View.GONE);
        }
    }

}