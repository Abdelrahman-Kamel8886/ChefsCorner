package com.abdok.chefscorner.Ui.Auth.SignUp.View;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Ui.Auth.Google.GooglePresenter;
import com.abdok.chefscorner.Ui.Auth.Google.IGooglePresenter;
import com.abdok.chefscorner.Ui.Auth.Google.IGoogleView;
import com.abdok.chefscorner.Ui.Auth.SignUp.Presenter.ISignUpPresenter;
import com.abdok.chefscorner.Ui.Auth.SignUp.Presenter.SignUpPresenter;
import com.abdok.chefscorner.Utils.Helpers.SnackBarHelper;
import com.abdok.chefscorner.databinding.FragmentLoginBinding;
import com.abdok.chefscorner.databinding.FragmentRegiesterBinding;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.material.snackbar.Snackbar;


public class RegiesterFragment extends Fragment implements ISignUpView , IGoogleView {

    private static final int RC_SIGN_IN = 123 ;

    FragmentRegiesterBinding binding;
    ISignUpPresenter presenter;

    IGooglePresenter googlePresenter;

    private GoogleSignInClient mGoogleSignInClient;
    private ActivityResultLauncher<Intent> googleSignInLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_regiester, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentRegiesterBinding.bind(view);

        presenter = new SignUpPresenter(this);
        googlePresenter = new GooglePresenter(this);

        initializeGoogle();

        onClicks();
    }

    private void initializeGoogle(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        // Initialize the ActivityResultLauncher
        googleSignInLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    if (signInResult.isSuccess()) {
                        GoogleSignInAccount account = signInResult.getSignInAccount();
                        handleGoogleSignInResult(account);
                    } else {
                        showInformation(getString(R.string.google_sign_in_failed));
                    }
                }
            }
        });
    }

    private void signInWithGoogle() {
        binding.progressBar.setVisibility(View.VISIBLE);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        googleSignInLauncher.launch(signInIntent); // Launch the sign-in intent
    }

    private void handleGoogleSignInResult(GoogleSignInAccount account) {
        googlePresenter.handleGoogleSignIn(account);
    }

    private void onClicks(){
        binding.backBtn.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        binding.loginTxt.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        binding.signBtn.setOnClickListener(v -> validation());
        binding.googleBtn.setOnClickListener(v -> signInWithGoogle());
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
        SnackBarHelper.showCustomSnackBar(requireActivity(), msg, R.color.errorRed, Gravity.BOTTOM);
    }

    @Override
    public void onGoogleAuthSuccess() {
        navigateToBase();
    }

    @Override
    public void onGoogleAuthFailure(String message) {
        showInformation(message);
    }

    @Override
    public void navigateToBase() {
        binding.progressBar.setVisibility(View.GONE);
        Navigation.findNavController(requireView()).navigate(R.id.action_regiesterFragment_to_baseFragment);
    }
}