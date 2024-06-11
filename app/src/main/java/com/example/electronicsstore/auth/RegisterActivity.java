package com.example.electronicsstore.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.electronicsstore.R;
import com.example.electronicsstore.database.DatabaseHelper;
import com.example.electronicsstore.databinding.ActivityRegisterBinding;
import com.example.electronicsstore.model.UsersModel;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    String name, email, phone, password;
    DatabaseHelper databaseHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);
        binding.rlTop.tvLabel.setText("Register");
        binding.rlTop.ivBack.setVisibility(View.VISIBLE);
        binding.rlTop.ivBack.setOnClickListener(v -> finish());
        binding.llBottom.setOnClickListener(v -> finish());
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidated()){
                    UsersModel users = new UsersModel(name, email, phone, password);
                    databaseHelper.register(users);
                    showMessage("Successfully Registered");
                    finish();
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}