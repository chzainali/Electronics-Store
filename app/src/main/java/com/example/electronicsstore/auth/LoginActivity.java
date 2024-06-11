package com.example.electronicsstore.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.electronicsstore.R;
import com.example.electronicsstore.admin.AdminMainActivity;
import com.example.electronicsstore.database.DatabaseHelper;
import com.example.electronicsstore.databinding.ActivityLoginBinding;
import com.example.electronicsstore.model.HelperClass;
import com.example.electronicsstore.model.UsersModel;
import com.example.electronicsstore.user.UserMainActivity;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    String adminEmail = "admin@gmail.com";
    String adminPassword = "admin@123";
    String userName, password;
    DatabaseHelper databaseHelper;
    Boolean checkDetails = false;
    List<UsersModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper = new DatabaseHelper(this);

        // Set the label for the top text view
        binding.rlTop.tvLabel.setText("Login");

        // Set a click listener to navigate to the RegisterActivity when the register layout is clicked
        binding.llRegister.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        // Set a click listener for the login button
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidated()) {
                    if (userName.contentEquals(adminEmail) && password.contentEquals(adminPassword)) {
                        HelperClass.checkUser = "Admin";
                        startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
                        finish();
                    }
                    else {
                        list = databaseHelper.getAllUsers();
                        for (UsersModel users : list) {
                            if (userName.equals(users.getUserName()) && password.equals(users.getPassword())) {
                                HelperClass.checkUser = "User";
                                checkDetails = true;
                                showMessage("Successfully Login");
                                HelperClass.users = users;
                                startActivity(new Intent(LoginActivity.this, UserMainActivity.class));
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                finish();
                                break;
                            }
                        }
                        if (!checkDetails) {
                            showMessage("Wrong Credentials...\nPlease check email or password");
                        }
                    }
                }
            }
        });
    }

    // Validate user input for email and password
    private Boolean isValidated() {
        userName = binding.etUserName.getText().toString().trim();
        password = binding.etPassword.getText().toString().trim();

        if (userName.isEmpty()) {
            showMessage("Please enter user name");
            return false;
        }

        if (password.isEmpty()) {
            showMessage("Please enter password");
            return false;
        }

        return true;
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}