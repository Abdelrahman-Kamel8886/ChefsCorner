package com.abdok.chefscorner.Ui.Splash;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdok.chefscorner.Data.DataSources.Local.SharedPreference.SharedPreferenceDataSource;
import com.abdok.chefscorner.Models.UserDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Utils.SharedModel;
import com.abdok.chefscorner.databinding.FragmentSplashBinding;

public class SplashFragment extends Fragment {
    FragmentSplashBinding binding;
    private static final int SPLASH_TIME_OUT = 4000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentSplashBinding.bind(view);
        binding.splashImage.setSpeed(1.8f);
        delay();
    }

    private void delay() {
        new Handler().postDelayed(() -> {
            checkUser();
            },SPLASH_TIME_OUT);
    }

    private void checkUser() {
        UserDTO user = SharedPreferenceDataSource.getInstance().getUser();
        boolean isFirstLaunch = SharedPreferenceDataSource.getInstance().isFirstTimeLaunch();

        if (isFirstLaunch){
            SharedPreferenceDataSource.getInstance().setFirstTimeLaunch();
            NavHostFragment.findNavController(SplashFragment.this).navigate(R.id.action_splashFragment_to_onboardingFragment);
        }
        else{
            if (user!=null){
                SharedModel.setUser(user);
                NavHostFragment.findNavController(SplashFragment.this).navigate(R.id.action_splashFragment_to_baseFragment);
            }
            else {
                NavHostFragment.findNavController(SplashFragment.this).navigate(R.id.action_splashFragment_to_loginFragment);
            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}