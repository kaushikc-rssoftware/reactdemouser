package com.example.merchantdemo;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.merchantdemo.dataClass.ViewInvoiceData;
import com.example.merchantdemo.databinding.ActivityDashbordBinding;
import com.example.merchantdemo.ui.home.HomeFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class Dashbord extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDashbordBinding binding;
    NavController navController;
    public static List<ViewInvoiceData> viewInvoiceDataSaved = new ArrayList<ViewInvoiceData>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashbordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }

        setSupportActionBar(binding.appBarDashbord.toolbar);
//        getSupportActionBar().setTitle("Central Infrastructure");
//        getSupportActionBar().setTitle("");


//        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_manage_templates, R.id.nav_create_invoice, R.id.nav_reports, R.id.nav_logout, R.id.nav_home)
                .setOpenableLayout(binding.drawerLayout)
                .build();
        /*NavController*/
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashbord);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(menuItem -> {
            finish();
            return true;
        });
        /*navigationView.getMenu().findItem(R.id.nav_reports).setOnMenuItemClickListener(menuItem -> {
            startActivity(new Intent(this, Dfi.class));
            drawer.closeDrawers();
            return true;
        });*/
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashbord);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        // Get the current fragment
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.nav_home);
        binding.drawerLayout.closeDrawers();
        // Check if the current fragment is the home fragment
        if (currentFragment instanceof HomeFragment) {
            // If it's the home fragment, close the application
            finish();
        } /*else if (currentFragment instanceof ManageTemplatesFragment){
            navController.navigate(R.id.nav_home);
        }*/ else {
            // If it's not the home fragment, let the system handle the back button press
            super.onBackPressed();
        }
    }
}