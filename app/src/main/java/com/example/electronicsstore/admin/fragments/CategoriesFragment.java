package com.example.electronicsstore.admin.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.electronicsstore.R;
import com.example.electronicsstore.adapter.CategoriesAdapter;
import com.example.electronicsstore.database.DatabaseHelper;
import com.example.electronicsstore.databinding.FragmentAddProductBinding;
import com.example.electronicsstore.databinding.FragmentCategoriesBinding;
import com.example.electronicsstore.model.CategoryModel;
import com.example.electronicsstore.model.HelperClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoriesFragment extends Fragment {
    private FragmentCategoriesBinding binding;
    CategoriesAdapter categoriesAdapter;
    DatabaseHelper databaseHelper;
    List<CategoryModel> list = new ArrayList<>();

    public CategoriesFragment() {
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
        binding = FragmentCategoriesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseHelper = new DatabaseHelper(requireContext());
        binding.rlTop.tvLabel.setText("Categories");
        if (Objects.equals(HelperClass.checkUser, "Admin")){
            binding.rlAdd.setVisibility(View.VISIBLE);
        }else{
            binding.rlAdd.setVisibility(View.GONE);
        }

        binding.rlAdd.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("checkFrom", "Add");
            Navigation.findNavController(view1).navigate(R.id.action_categoriesFragment_to_addCategoryFragment, bundle);
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();

        list.clear();
        list.addAll(databaseHelper.getAllCategories());

        if (list.size() > 0) {
            binding.noDataFound.setVisibility(View.GONE);
            binding.rvGoals.setVisibility(View.VISIBLE);
            binding.lineView.setVisibility(View.VISIBLE);
            binding.rvGoals.setLayoutManager(new LinearLayoutManager(requireContext()));
            categoriesAdapter = new CategoriesAdapter(list, requireContext(), databaseHelper, "admin");
            binding.rvGoals.setAdapter(categoriesAdapter);
            categoriesAdapter.notifyDataSetChanged();
        } else {
            binding.noDataFound.setVisibility(View.VISIBLE);
            binding.lineView.setVisibility(View.GONE);
            binding.rvGoals.setVisibility(View.GONE);
        }
    }

}