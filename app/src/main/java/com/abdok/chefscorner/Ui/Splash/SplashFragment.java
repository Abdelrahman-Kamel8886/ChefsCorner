package com.abdok.chefscorner.Ui.Splash;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdok.chefscorner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashFragment extends Fragment {

    private static final int SPLASH_TIME_OUT = 3000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        delay();
    }

    private void delay() {
        new Handler().postDelayed(() -> {checkUser();},SPLASH_TIME_OUT);
    }

    private void checkUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            NavHostFragment.findNavController(SplashFragment.this).navigate(R.id.action_splashFragment_to_homeFragment);
        }
        else {
            NavHostFragment.findNavController(SplashFragment.this).navigate(R.id.action_splashFragment_to_loginFragment);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}