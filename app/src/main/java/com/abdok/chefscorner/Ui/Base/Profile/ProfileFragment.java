package com.abdok.chefscorner.Ui.Base.Profile;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abdok.chefscorner.Data.Models.UserDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Ui.Base.IBaseView;
import com.abdok.chefscorner.Utils.SharedModel;
import com.abdok.chefscorner.databinding.FragmentProfileBinding;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;


public class ProfileFragment extends Fragment implements IProfileView {

    FragmentProfileBinding binding;
    IBaseView baseView;
    IProfilePresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentProfileBinding.bind(view);
        baseView = (IBaseView) getParentFragment().getParentFragment();
        baseView.hideBottomNav();
        presenter = new ProfilePresenter(this, getString(R.string.default_web_client_id));
        showUserData(SharedModel.getUser());
        onClicks();
    }

    private void onClicks(){
        binding.backBtn.setOnClickListener(v -> Navigation.findNavController(requireView()).navigateUp());
        binding.logoutBtn.setOnClickListener(v -> presenter.logout());
    }


    @Override
    public void showUserData(UserDTO user) {
        binding.userName.setText(user.getName());
        if (user.getPhotoUrl()!=null){
            Glide.with(requireContext()).load(user.getPhotoUrl()).into(binding.avatarImg);
        }
    }

    @Override
    public void navigateToLogin() {
        NavController navController = NavHostFragment.findNavController(requireActivity().getSupportFragmentManager().findFragmentById(R.id.container));
        navController.navigate(R.id.loginFragment, null, new NavOptions.Builder()
                .setPopUpTo(R.id.baseFragment, true)
                .build());
    }
    @Override
    public void showInformation(String msg){
        Snackbar snackbar = Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.BLACK);
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(16);
        snackbar.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}