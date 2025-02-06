package com.abdok.chefscorner.Ui.Auth.SignUp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.abdok.chefscorner.R;
import com.abdok.chefscorner.databinding.FragmentRegiesterBinding;
import com.google.android.material.snackbar.Snackbar;


public class RegiesterFragment extends Fragment implements ISignUpView {

    FragmentRegiesterBinding binding;
    ISignUpPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_regiester, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentRegiesterBinding.bind(view);
        presenter = new SignUpPresenter(this);
        onClicks();
    }
    private void onClicks(){



        binding.backBtn.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        binding.loginTxt.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        binding.signBtn.setOnClickListener(v -> validation());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }

    private void validation(){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.signBtn.setEnabled(false);
        String name = binding.editName.getText().toString();
        String email = binding.editEmail.getText().toString();
        String password = binding.editpassword.getText().toString();
        String confirmPassword = binding.editconfirmpassword.getText().toString();
        presenter.validateData(name, email, password, confirmPassword);
    }

    @Override
    public void showInformation(String msg) {
        binding.progressBar.setVisibility(View.GONE);
        binding.signBtn.setEnabled(true);
        Snackbar snackbar = Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.BLACK);
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(16);
        snackbar.show();
    }

    @Override
    public void navigateToHome() {
        Navigation.findNavController(requireView()).navigate(R.id.action_regiesterFragment_to_homeFragment);
    }
}