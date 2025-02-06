package com.abdok.chefscorner.Ui.Auth.SignUp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdok.chefscorner.R;
import com.abdok.chefscorner.databinding.FragmentRegiesterBinding;


public class RegiesterFragment extends Fragment implements ISignUpView {

    FragmentRegiesterBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_regiester, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentRegiesterBinding.bind(view);
        onClicks();
    }
    private void onClicks(){
        binding.backBtn.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        binding.loginTxt.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }
}