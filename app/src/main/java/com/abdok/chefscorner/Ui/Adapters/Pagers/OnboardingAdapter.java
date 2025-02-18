package com.abdok.chefscorner.Ui.Adapters.Pagers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.abdok.chefscorner.Ui.Onboarding.OnboardingPageFragment;

public class OnboardingAdapter extends FragmentStateAdapter {

    public OnboardingAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return OnboardingPageFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}