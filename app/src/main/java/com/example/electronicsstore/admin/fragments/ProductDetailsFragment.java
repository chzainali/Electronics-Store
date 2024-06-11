package com.example.electronicsstore.admin.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.electronicsstore.R;
import com.example.electronicsstore.database.DatabaseHelper;
import com.example.electronicsstore.databinding.FragmentProductDetailsBinding;
import com.example.electronicsstore.model.CartModel;
import com.example.electronicsstore.model.HelperClass;
import com.example.electronicsstore.model.ProductModel;
import com.example.electronicsstore.model.ProductsModel;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ProductDetailsFragment extends Fragment {
    private FragmentProductDetailsBinding binding;
    ProductsModel productsModel, previousModel;
    String currentDateAndTime;
    DatabaseHelper databaseHelper;
    List<CartModel> list = new ArrayList<>();
    CartModel updatedModel;
    TextInputEditText priceEt;
    TextInputEditText quantityEt;
    private double actualPrice;
    double totalPrice;
    java.text.DecimalFormat df;
    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            previousModel = (ProductsModel) getArguments().getSerializable("data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        df = new java.text.DecimalFormat("0.00");
        binding.rlTop.tvLabel.setText("Product Details");
        databaseHelper = new DatabaseHelper(requireContext());
        binding.rlTop.ivBack.setVisibility(View.VISIBLE);
        binding.rlTop.ivBack.setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp());

    }

    @SuppressLint("SetTextI18n")
    public void showDialog() {
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.item_cart_dialog);

        TextInputEditText nameEt = (TextInputEditText) dialog.findViewById(R.id.nameEt);
        priceEt = (TextInputEditText) dialog.findViewById(R.id.priceEt);
        quantityEt = (TextInputEditText) dialog.findViewById(R.id.quantityEt);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        Button btnConfirm = (Button) dialog.findViewById(R.id.btnConfirm);

        nameEt.setText(productsModel.getName());
        String formattedPrice;
        if (productsModel.getOfferPrice().isEmpty()) {
            formattedPrice = df.format(Double.parseDouble(productsModel.getOriginalPrice()));
        } else {
            formattedPrice = df.format(Double.parseDouble(productsModel.getOfferPrice()));
        }
        priceEt.setText("$" + formattedPrice);

        if (productsModel != null) {
            if (productsModel.getOfferPrice().isEmpty()) {
                actualPrice = Double.parseDouble(productsModel.getOriginalPrice());
            } else {
                actualPrice = Double.parseDouble(productsModel.getOfferPrice());
            }
            quantityEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    calculateTotalPrice(editable.toString());
                }
            });

        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = quantityEt.getText().toString();

                if (quantity.isEmpty()) {
                    showMessage("Please enter quantity");
                } else if (quantity.contentEquals("0")) {
                    showMessage("Please enter valid quantity");
                } else if (Integer.parseInt(quantity) > Integer.parseInt(productsModel.getQuantity())) {
                    showMessage("Total available products are " + productsModel.getQuantity() + ",\nPlease enter valid quantity");
                } else {
                    int remainingQuantity = Integer.parseInt(productsModel.getQuantity()) - Integer.parseInt(quantity);
                    productsModel.setQuantity(String.valueOf(remainingQuantity));
                    databaseHelper.updateProduct(productsModel);
                    list = databaseHelper.getCartData(HelperClass.users.getId());
                    if (!list.isEmpty()) {
                        for (int i = 0; i < list.size(); i++) {
                            if (productsModel.getId() == list.get(i).getProductId() && Objects.equals(productsModel.getName(), list.get(i).getName())) {
                                updatedModel = list.get(i);
                                break;
                            }
                        }
                    }
                    getCurrentDateAndTime();

                    if (updatedModel != null) {

                        double finalPrice = Double.parseDouble(updatedModel.getPrice()) + totalPrice;
                        int finalQuantity = Integer.parseInt(updatedModel.getQuantity()) + Integer.parseInt(quantity);
                        updatedModel.setPrice(String.valueOf(finalPrice));
                        updatedModel.setQuantity(String.valueOf(finalQuantity));
                        databaseHelper.updateCart(updatedModel);
                    } else {
                        CartModel cartModel = new CartModel(HelperClass.users.getId(), productsModel.getCategoryId(),
                                productsModel.getId(), productsModel.getName(), String.valueOf(totalPrice),
                                quantity, productsModel.getDescription(), productsModel.getImageUri());
                        databaseHelper.insertCart(cartModel);
                    }
                    showMessage("Added to Cart Successfully");
                    Navigation.findNavController(requireView()).navigateUp();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();

    }

    @SuppressLint("SetTextI18n")
    private void calculateTotalPrice(String quantityStr) {
        if (!quantityStr.isEmpty()) {
            try {
                long quantity = Long.parseLong(quantityStr);
                totalPrice = actualPrice * quantity;
                String formattedPrice = df.format(totalPrice);
                priceEt.setText("$" + formattedPrice);
            } catch (NumberFormatException e) {
                // Handle the case where parsing fails
                priceEt.setText("Invalid quantity");
                e.printStackTrace(); // Log the exception for debugging purposes
            }
        } else {
            priceEt.setText("$0");
        }
    }

    private void showMessage(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (previousModel != null) {
            productsModel = databaseHelper.getProductByIdAndCategory(previousModel.getId(), previousModel.getCategoryId());
            binding.etName.setText(productsModel.getName());
            String formattedPrice;
            if (productsModel.getOfferPrice().isEmpty()) {
                formattedPrice = df.format(Double.parseDouble(productsModel.getOriginalPrice()));
            } else {
                formattedPrice = df.format(Double.parseDouble(productsModel.getOfferPrice()));
            }
            binding.etPrice.setText("SAR" + formattedPrice);
            binding.etQuantity.setText(productsModel.getQuantity());
            binding.etDescription.setText(productsModel.getDescription());
            Glide.with(this)
                    .asBitmap()
                    .load(Uri.parse(productsModel.getImageUri()))
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            // Now you have the Bitmap, you can set it as the image resource.
                            binding.ivImage.setImageBitmap(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                            // This is called when the Drawable is cleared, for example, when the view is recycled.
                        }
                    });
            if (Objects.equals(HelperClass.checkUser, "User")){
                if (productsModel.getQuantity().contentEquals("0")) {
                    binding.btnCart.setVisibility(View.GONE);
                } else {
                    binding.btnCart.setVisibility(View.VISIBLE);
                }
            }else{
                binding.btnCart.setText("Update");
            }
            binding.btnCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Objects.equals(HelperClass.checkUser, "User")){
                        showDialog();
                    }else{
                        Bundle bundle = new Bundle();
                        bundle.putString("checkFrom", "Update");
                        bundle.putSerializable("data", productsModel);
                        Navigation.findNavController(view).navigate(R.id.action_productDetailsFragment_to_addProductFragment, bundle);
                    }
                }
            });

        }
    }

    private void getCurrentDateAndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.getDefault());
        currentDateAndTime = sdf.format(new Date());
    }

}