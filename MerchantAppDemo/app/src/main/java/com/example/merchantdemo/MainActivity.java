package com.example.merchantdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.merchantdemo.ui.createInvoice.CreateInvoiceFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.letTheUserLogIn)
    Button letTheUserLogIn;


    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String PERMISSIONS_REQUESTED_KEY = "permissions_requested";
    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Objects.requireNonNull(getSupportActionBar()).hide();
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.white, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }


        // Check if the app has the necessary permissions
//        if (checkPermissions()) {
//            // Permissions are granted, proceed with your app logic
////            Toast.makeText(this, "Proceed to login or register", Toast.LENGTH_SHORT).show();
//        } else {
//            // Check if permissions were previously requested
//            if (wasPermissionsRequestedBefore()) {
//                // Permissions were requested before, show a message or take appropriate action
//                showPermissionsDeniedMessage();
//            } else {
//                // Request permissions
//                requestPermissions();
//                // Mark that permissions have been requested
//                markPermissionsRequested();
//            }
//        }
    }

    @OnClick(R.id.letTheUserLogIn)
    public void setLetTheUserLogIn(View view){
        startActivity(new Intent(getApplicationContext(),Dashbord.class));
        finish();
    }

    private boolean checkPermissions() {
        // Check for camera, read, and write permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true; // Permissions are granted on lower versions
    }

    private void showPermissionsDeniedMessage() {
        // Show a message or take appropriate action when permissions are denied
        // You can display a dialog, a toast, or any other user-friendly message
        // and inform the user that the app cannot proceed without the required permissions
        // and close the app if needed.
        // For example:
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions Required")
                .setMessage("This app requires camera, read, and write permissions to function properly.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish(); // Optionally close the app
                    }
                })
                .setCancelable(false)
                .show();
    }

    private boolean wasPermissionsRequestedBefore() {
        // Check if permissions were previously requested
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return preferences.getBoolean(PERMISSIONS_REQUESTED_KEY, false);
    }

    private void markPermissionsRequested() {
        // Mark that permissions have been requested
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(PERMISSIONS_REQUESTED_KEY, true);
        editor.apply();
    }

    private void requestPermissions() {
        // Request camera, read, and write permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && allPermissionsGranted(grantResults)) {
                // Permissions granted, proceed with your app logic
                Toast.makeText(this, "Proceed to login or register", Toast.LENGTH_SHORT).show();
            } else {
                // Permissions denied, show a message or take appropriate action
                showPermissionsDeniedMessage();
            }
        }
    }

    private boolean allPermissionsGranted(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}