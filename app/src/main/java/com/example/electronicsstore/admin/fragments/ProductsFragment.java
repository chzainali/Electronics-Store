package com.example.electronicsstore.admin.fragments;
import static com.example.electronicsstore.admin.AdminMainActivity.listOfProducts;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.electronicsstore.R;
import com.example.electronicsstore.adapter.ProductsAdapter;
import com.example.electronicsstore.database.DatabaseHelper;
import com.example.electronicsstore.databinding.FragmentProductsBinding;
import com.example.electronicsstore.model.CategoryModel;
import com.example.electronicsstore.model.HelperClass;
import com.example.electronicsstore.model.ProductModel;
import com.example.electronicsstore.model.ProductsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ProductsFragment extends Fragment {
    private FragmentProductsBinding binding;
    CategoryModel categoryModel;
    ProductsAdapter productsAdapter;
    DatabaseHelper databaseHelper;
    List<ProductsModel> list = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryModel = (CategoryModel) getArguments().getSerializable("data");
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProductsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set the label based on the categoryModel
        if (categoryModel != null) {
            binding.rlTop.tvLabel.setText(categoryModel.getName() + " Products");
        }

        if (Objects.equals(HelperClass.checkUser, "Admin")){
            binding.rlAdd.setVisibility(View.VISIBLE);
        }else{
            binding.rlAdd.setVisibility(View.GONE);
        }

        // Initialize DatabaseHelper and set up the UI components
        databaseHelper = new DatabaseHelper(requireContext());
        binding.rlTop.ivBack.setVisibility(View.VISIBLE);
        binding.rlTop.ivBack.setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp());

        binding.rvGoals.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        productsAdapter = new ProductsAdapter(list, requireContext(), "products", databaseHelper);
        binding.rvGoals.setAdapter(productsAdapter);

        // Set up the SearchView listener for filtering products
        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    productsAdapter.setList(list);
                } else {
                    filter(newText);
                }
                return false;
            }
        });

        binding.rlAdd.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("checkFrom", "Add");
            bundle.putSerializable("data", categoryModel);
            Navigation.findNavController(view1).navigate(R.id.action_navigation_products_to_addProductFragment, bundle);
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();

        list.clear();
        if (categoryModel != null) {
            list.addAll(databaseHelper.getProductsByCategory(categoryModel.getId()));
        }

        if (list.size() > 0) {
            binding.noDataFound.setVisibility(View.GONE);
            binding.rvGoals.setVisibility(View.VISIBLE);
            binding.rvGoals.setLayoutManager(new GridLayoutManager(requireContext(), 2));
            productsAdapter = new ProductsAdapter(list, requireContext(), "products", databaseHelper);
            binding.rvGoals.setAdapter(productsAdapter);
            productsAdapter.notifyDataSetChanged();
        } else {
            binding.noDataFound.setVisibility(View.VISIBLE);
            binding.rvGoals.setVisibility(View.GONE);
        }
    }

    public void filter(String text) {
        List<ProductsModel> filteredList = new ArrayList<>();
        for (ProductsModel model : list) {
            if (model.getName().toLowerCase(Locale.ROOT).contains(text.toLowerCase())) {
                filteredList.add(model);
            }
        }
        productsAdapter.setList(filteredList);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}