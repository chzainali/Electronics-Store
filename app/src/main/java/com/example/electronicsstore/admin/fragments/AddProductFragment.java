package com.example.electronicsstore.admin.fragments;

import static com.example.electronicsstore.admin.AdminMainActivity.listOfProducts;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.electronicsstore.database.DatabaseHelper;
import com.example.electronicsstore.databinding.FragmentAddProductBinding;
import com.example.electronicsstore.model.CategoryModel;
import com.example.electronicsstore.model.ProductModel;
import com.example.electronicsstore.model.ProductsModel;

import java.text.DecimalFormat;

public class AddProductFragment extends Fragment {
    private FragmentAddProductBinding binding;
    String name, price, offerPrice, quantity, description, imageUri = "";
    DatabaseHelper databaseHelper;
    CategoryModel categoryModel;
    ProductsModel updateModel;
    String checkFrom;
    DecimalFormat df;


    public AddProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            checkFrom = getArguments().getString("checkFrom");
            if (checkFrom.contentEquals("Add")) {
                categoryModel = (CategoryModel) getArguments().getSerializable("data");
            } else {
                updateModel = (ProductsModel) getArguments().getSerializable("data");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddProductBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        df = new DecimalFormat("0.00");
        databaseHelper = new DatabaseHelper(requireContext());
        binding.rlTop.ivBack.setVisibility(View.VISIBLE);
        binding.rlTop.ivBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        if (checkFrom.contentEquals("Add")) {
            binding.rlTop.tvLabel.setText("Add " + categoryModel.getName() + " Product");
        } else {
            binding.rlTop.tvLabel.setText("Update Product");
            binding.etName.setText(updateModel.getName());
            String formattedPrice1 = df.format(Double.parseDouble(updateModel.getOriginalPrice()));
            binding.priceEt.setText(formattedPrice1);
            if (!updateModel.getOfferPrice().isEmpty()) {
                String formattedPrice2 = df.format(Double.parseDouble(updateModel.getOfferPrice()));
                binding.offerPriceEt.setText(formattedPrice2);
            }
            binding.quantityEt.setText(updateModel.getQuantity());
            binding.etDescription.setText(updateModel.getDescription());
            Glide.with(binding.ivImage)
                    .asBitmap()
                    .load(Uri.parse(updateModel.getImageUri()))
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            // Set the Bitmap as the image resource
                            binding.ivImage.setImageBitmap(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                            // Called when the Drawable is cleared, for example, when the view is recycled
                        }
                    });

            imageUri = updateModel.getImageUri();
            binding.btnAdd.setText("Update");
        }

        binding.rlTop.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

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
                // Validate user input and proceed with adding or updating the product
                if (isValidated()) {
                    offerPrice = binding.offerPriceEt.getText().toString();

                    // Check if offer price is empty and set it to an empty string
                    if (offerPrice.isEmpty()) {
                        offerPrice = "";
                    }

                    // Check if the operation is to add a new product
                    if (categoryModel != null) {
                        // Create a new ProductsModel object and insert it into the database
                        ProductsModel productsModel = new ProductsModel(categoryModel.getId(), name, price, offerPrice, quantity, description, imageUri);
                        databaseHelper.insertProduct(productsModel);
                        showMessage("Successfully Added");
                    } else {
                        // The operation is to update an existing product
                        ProductsModel productsModel = new ProductsModel(updateModel.getId(), updateModel.getCategoryId(), name, price, offerPrice, quantity, description, imageUri);
                        databaseHelper.updateProduct(productsModel);
                        showMessage("Updated Successfully");
                    }

                    Navigation.findNavController(view).navigateUp();
                }
            }
        });
    }

    private Boolean isValidated() {
        name = binding.etName.getText().toString().trim();
        price = binding.priceEt.getText().toString().trim();
        offerPrice = binding.offerPriceEt.getText().toString().trim();
        quantity = binding.quantityEt.getText().toString().trim();
        description = binding.etDescription.getText().toString().trim();

        // Check if an image is selected
        if (imageUri.isEmpty()) {
            showMessage("Please pick an image");
            return false;
        }

        // Check if the product name is empty
        if (name.isEmpty()) {
            showMessage("Please enter a name");
            return false;
        }

        // Check if the price is empty
        if (price == null || price.isEmpty()) {
            showMessage("Please enter a price");
            return false;
        }

        // Check if the offer price is provided and less than the regular price
        if (offerPrice != null && !offerPrice.isEmpty()) {
            try {
                double regularPriceValue = Double.parseDouble(price);
                double offerPriceValue = Double.parseDouble(offerPrice);

                // Offer price should be less than the original price
                if (offerPriceValue >= regularPriceValue) {
                    showMessage("Offer price should be less than the original price");
                    return false;
                }
            } catch (NumberFormatException e) {
                // Handle the case where price or offerPrice is not a valid double
                e.printStackTrace();
                showMessage("Invalid price or offer price");
                return false;
            }
        }

        // Check if the quantity is empty
        if (quantity.isEmpty()) {
            showMessage("Please enter a quantity");
            return false;
        }

        // Check if the quantity is greater than 0
        if (quantity.contentEquals("0")) {
            showMessage("Please enter a quantity greater than 0");
            return false;
        }

        // Check if the description is empty
        if (description.isEmpty()) {
            showMessage("Please enter a description");
            return false;
        }

        // Validation successful
        return true;
    }

    private void showMessage(String message) {
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