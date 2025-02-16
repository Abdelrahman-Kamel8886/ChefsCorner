package com.abdok.chefscorner.Ui.Base;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.abdok.chefscorner.CustomViews.GuestDialog.GuestDialog;
import com.abdok.chefscorner.Data.DataSources.Local.SharedPreference.SharedPreferenceDataSource;
import com.abdok.chefscorner.Data.Models.UserDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Utils.SharedModel;
import com.abdok.chefscorner.databinding.FragmentBaseBinding;
import com.bumptech.glide.Glide;

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
        changeStatusBarColor(R.color.white);
        CbnMenuItem[] menuItems = new CbnMenuItem[]{
                new CbnMenuItem(R.drawable.ic_baseline_home_24, R.drawable.ic_baseline_home_avd, R.id.homeFragment),
                new CbnMenuItem(R.drawable.ic_baseline_search_24, R.drawable.ic_baseline_search_avd, R.id.searchFragment),
                new CbnMenuItem(R.drawable.ic_baseline_bookmark_border_24, R.drawable.ic_baseline_bookmark_avd, R.id.favoritesFragment),
                new CbnMenuItem(R.drawable.outline_calendar_today_24, R.drawable.ic_baseline_calendar_avd, R.id.planFragment)
        };
        binding.nav.setMenuItems(menuItems, 0);
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.container);
        NavController navController = navHostFragment.getNavController();
        binding.nav.setupWithNavController(navController);






        SharedModel.setUser(SharedPreferenceDataSource.getInstance().getUser());
        showUserData(SharedModel.getUser());

    }

    @Override
    public void showMainView() {
        binding.loadingLayout.setVisibility(View.GONE);
        binding.mainView.setVisibility(View.VISIBLE);
        binding.nav.setVisibility(View.VISIBLE);
        onClicks();
    }

    @Override
    public void hideBottomNav() {
        binding.nav.setVisibility(View.GONE);
        binding.header.setVisibility(View.GONE);
    }

    public void changeStatusBarColor(int colorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = requireActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(requireContext(), colorResId));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                View decor = requireActivity().getWindow().getDecorView();
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR); // Dark text/icons
            }
        }
    }

    private void showUserData(UserDTO user) {
        String username = user!=null ?user.getName() : getString(R.string.guest);
        binding.userName.setText("Hi, " + username);
        if (user!=null && user.getPhotoUrl() != null) {
            Glide.with(requireContext()).load(user.getPhotoUrl()).into(binding.avatarImg);
        }
    }

    private void onClicks() {
        binding.avatarImg.setOnClickListener(v -> {
            if (SharedModel.getUser() == null){
                showGuestDialog();
            }
            else {
                navigateToProfile();
            }

        });
    }

    private void navigateToProfile() {
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.container);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(R.id.profileFragment);
    }

//    private void showGuestBottomSheet() {
//        GuestBottomSheet guestBottomSheet = new GuestBottomSheet();
//        guestBottomSheet.show(getChildFragmentManager(), "guest_bottom_sheet");
//        guestBottomSheet.setOnItemClickListener(new GuestBottomSheet.OnItemClickListener() {
//            @Override
//            public void onCancelClick() {
//
//            }
//
//            @Override
//            public void onSignUpClick() {
//
//            }
//        });
//
//    }

    private void showGuestDialog() {
        GuestDialog guestDialog = new GuestDialog();
        guestDialog.show(getChildFragmentManager(), "GuestDialog");
        guestDialog.setOnItemClickListener(()->{
            navigateToLogin();
        });

    }

    private void navigateToLogin(){
        NavController navController = NavHostFragment.findNavController(requireActivity().getSupportFragmentManager().findFragmentById(R.id.container));
        navController.navigate(R.id.loginFragment, null, new NavOptions.Builder()
                .setPopUpTo(R.id.baseFragment, true)
                .build());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}