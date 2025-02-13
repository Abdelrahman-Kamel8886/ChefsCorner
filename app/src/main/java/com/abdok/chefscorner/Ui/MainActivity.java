package com.abdok.chefscorner.Ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.abdok.chefscorner.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}