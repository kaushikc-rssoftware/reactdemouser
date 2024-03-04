package com.example.dfiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.letTheUserLogIn)
    Button letTheUserLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.letTheUserLogIn)
    public void setLetTheUserLogIn(View view){
        startActivity(new Intent(getApplicationContext(),Dashbord.class));
    }
}