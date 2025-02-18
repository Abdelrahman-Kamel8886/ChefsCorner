package com.abdok.chefscorner.Ui.Onboarding;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abdok.chefscorner.Data.DataSources.Local.SharedPreference.SharedPreferenceDataSource;
import com.abdok.chefscorner.Models.UserDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Ui.Adapters.Pagers.OnboardingAdapter;
import com.abdok.chefscorner.Ui.Splash.SplashFragment;
import com.abdok.chefscorner.databinding.FragmentOnBoardingBinding;
import com.google.android.material.tabs.TabLayoutMediator;


public class OnboardingFragment extends Fragment {

    FragmentOnBoardingBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_on_boarding, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentOnBoardingBinding.bind(view);
        OnboardingAdapter adapter = new OnboardingAdapter(this);
        binding.viewPager.setAdapter(adapter);


        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                switch (position) {
                    case 0:
                        binding.back.setVisibility(View.GONE);
                        binding.next.setText(getString(R.string.next));
                        binding.progress.setProgress(30);
                        break;
                    case 1:
                        binding.back.setVisibility(View.VISIBLE);
                        binding.next.setText(getString(R.string.next));
                        binding.progress.setProgress(60);
                        break;
                    case 2:
                        binding.back.setVisibility(View.VISIBLE);
                        binding.next.setText(getString(R.string.get_started));
                        binding.progress.setProgress(100);
                        break;

                }

                binding.next.setOnClickListener(v -> {
                    if (position == 2) {
                        checkLoginStatus();
                    } else {
                        binding.viewPager.setCurrentItem(position + 1, true);
                    }
                });

                binding.back.setOnClickListener(v -> {
                    binding.viewPager.setCurrentItem(position - 1, true);
                });

                binding.btnSkip.setOnClickListener(v -> {
                    checkLoginStatus();
                });

            }
        });

    }

    private void checkLoginStatus() {
        UserDTO user = SharedPreferenceDataSource.getInstance().getUser();
        if (user != null) {
            navigateToHome();
        } else {
            navigateToLogin();
        }
    }

    private void navigateToHome() {
        NavHostFragment.findNavController(this).navigate(R.id.action_onboardingFragment_to_baseFragment);
    }

    private void navigateToLogin() {
        NavHostFragment.findNavController(this).navigate(R.id.action_onboardingFragment_to_loginFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}