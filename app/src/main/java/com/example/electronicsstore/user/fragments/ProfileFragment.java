package com.example.electronicsstore.user.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.electronicsstore.R;
import com.example.electronicsstore.database.DatabaseHelper;
import com.example.electronicsstore.databinding.FragmentContactUsBinding;
import com.example.electronicsstore.databinding.FragmentProfileBinding;
import com.example.electronicsstore.model.HelperClass;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    String name, email, phone, password;
    DatabaseHelper databaseHelper;

    public ProfileFragment() {
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
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.rlTop.tvLabel.setText("Profile");
        binding.rlTop.ivBack.setVisibility(View.VISIBLE);
        binding.rlTop.ivBack.setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp());
        databaseHelper = new DatabaseHelper(requireContext());

        binding.etNam.setText(HelperClass.users.getUserName());
        binding.etEmail.setText(HelperClass.users.getEmail());
        binding.etPhone.setText(HelperClass.users.getPhone());
        binding.etPassword.setText(HelperClass.users.getPassword());

        binding.btnSav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidated()){
                    HelperClass.users.setEmail(email);
                    HelperClass.users.setPhone(phone);
                    HelperClass.users.setPassword(password);
                    databaseHelper.updateUser(HelperClass.users);
                    showMessage("Successfully Saved");
                }
            }
        });

    }

    private Boolean isValidated(){
        name = binding.etNam.getText().toString().trim();
        email = binding.etEmail.getText().toString().trim();
        phone = binding.etPhone.getText().toString().trim();
        password = binding.etPassword.getText().toString().trim();

        if (name.isEmpty()){
            showMessage("Please enter user name");
            return false;
        }
        if (email.isEmpty()){
            showMessage("Please enter email");
            return false;
        }
        if (!(Patterns.EMAIL_ADDRESS).matcher(email).matches()) {
            showMessage("Please enter email in correct format");
            return false;
        }
        if (phone.isEmpty() || phone.length()<10){
            showMessage("Please enter correct phone number");
            return false;
        }
        if (password.isEmpty()){
            showMessage("Please enter password");
            return false;
        }

        return true;
    }

    private void showMessage(String message){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

}