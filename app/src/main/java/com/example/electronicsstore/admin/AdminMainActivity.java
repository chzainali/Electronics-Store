package com.example.electronicsstore.admin;

import android.os.Bundle;

import com.example.electronicsstore.R;
import com.example.electronicsstore.model.ProductModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.electronicsstore.databinding.ActivityAdminMainBinding;

import java.util.ArrayList;

public class AdminMainActivity extends AppCompatActivity {
    private ActivityAdminMainBinding binding;
    public static ArrayList<ProductModel> listOfProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.categoriesFragment, R.id.navigation_orders)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_admin_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}