package com.abdok.chefscorner.Base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdok.chefscorner.R;
import com.abdok.chefscorner.databinding.FragmentBaseBinding;

import np.com.susanthapa.curved_bottom_navigation.CbnMenuItem;


public class BaseFragment extends Fragment implements IBaseView {

    FragmentBaseBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentBaseBinding.bind(view);
        CbnMenuItem[] menuItems = new CbnMenuItem[] {
                new CbnMenuItem(R.drawable.ic_baseline_home_24, R.drawable.ic_baseline_home_avd,R.id.homeFragment),
                new CbnMenuItem(R.drawable.ic_baseline_search_24, R.drawable.ic_baseline_search_avd,R.id.searchFragment),
                new CbnMenuItem(R.drawable.ic_baseline_bookmark_border_24, R.drawable.ic_baseline_bookmark_avd,R.id.planFragment)
        };
        binding.nav.setMenuItems(menuItems, 0);
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.container);
        NavController navController = navHostFragment.getNavController();
        binding.nav.setupWithNavController(navController);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void showMainView() {
        binding.loadingLayout.setVisibility(View.GONE);
        binding.mainView.setVisibility(View.VISIBLE);
    }
}