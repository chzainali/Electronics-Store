package com.example.electronicsstore.admin.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.electronicsstore.R;
import com.example.electronicsstore.database.DatabaseHelper;
import com.example.electronicsstore.databinding.FragmentAddCategoryBinding;
import com.example.electronicsstore.databinding.FragmentCategoriesBinding;
import com.example.electronicsstore.model.CategoryModel;

public class AddCategoryFragment extends Fragment {
    private FragmentAddCategoryBinding binding;
    String name, imageUri = "";
    DatabaseHelper databaseHelper;

    public AddCategoryFragment() {
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
        binding = FragmentAddCategoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseHelper = new DatabaseHelper(requireContext());
        binding.rlTop.tvLabel.setText("Add Products Category");
        binding.rlTop.ivBack.setVisibility(View.VISIBLE);
        binding.rlTop.ivBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        binding.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                startActivityForResult(intent, 100);
            }
        });

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidated()){
                    CategoryModel categoryModel = new CategoryModel(name, imageUri);
                    databaseHelper.insertCategory(categoryModel);
                    showMessage("Successfully Added");
                    Navigation.findNavController(view).navigateUp();
                }
            }
        });

    }

    private Boolean isValidated(){
        name = binding.nameEt.getText().toString().trim();

        if (imageUri.isEmpty()){
            // Show a message if the image is not selected
            showMessage("Please pick an image");
            return false;
        }

        if (name.isEmpty()){
            // Show a message if the name is not entered
            showMessage("Please enter a name");
            return false;
        }

        return true;
    }

    private void showMessage(String message){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            imageUri = data.getData().toString();
            binding.ivImage.setImageURI(data.getData());
        }
    }

}