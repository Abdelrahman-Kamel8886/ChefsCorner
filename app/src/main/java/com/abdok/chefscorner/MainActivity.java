package com.abdok.chefscorner;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.abdok.chefscorner.databinding.ActivityMainBinding;

import np.com.susanthapa.curved_bottom_navigation.CbnMenuItem;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        @SuppressLint("ResourceType") CbnMenuItem[] menuItems = new CbnMenuItem[] {
                new CbnMenuItem(R.drawable.ic_baseline_bookmark_border_24, R.drawable.ic_baseline_bookmark_avd,1),
                new CbnMenuItem(R.drawable.ic_baseline_bookmark_border_24, R.drawable.ic_baseline_bookmark_avd,2),
                new CbnMenuItem(R.drawable.ic_baseline_bookmark_border_24, R.drawable.ic_baseline_bookmark_avd,3)
        };
        binding.nav.setMenuItems(menuItems, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}