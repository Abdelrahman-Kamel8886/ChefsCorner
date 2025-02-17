package com.abdok.chefscorner.Ui.Auth.Login.View;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Ui.Auth.Google.GooglePresenter;
import com.abdok.chefscorner.Ui.Auth.Google.IGooglePresenter;
import com.abdok.chefscorner.Ui.Auth.Google.IGoogleView;
import com.abdok.chefscorner.Ui.Auth.Login.Presenter.ILoginPresenter;
import com.abdok.chefscorner.Ui.Auth.Login.Presenter.LoginPresenter;
import com.abdok.chefscorner.Utils.Helpers.SnackBarHelper;
import com.abdok.chefscorner.databinding.FragmentLoginBinding;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.material.snackbar.Snackbar;


public class LoginFragment extends Fragment implements ILoginView , IGoogleView {

    private static final int RC_SIGN_IN = 123 ;
    ILoginPresenter loginPresenter;
    IGooglePresenter googlePresenter;
    FragmentLoginBinding binding;

    private GoogleSignInClient mGoogleSignInClient;
    private ActivityResultLauncher<Intent> googleSignInLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentLoginBinding.bind(view);

        loginPresenter = new LoginPresenter(this);
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

    private void onClicks(){
        binding.loginBtn.setOnClickListener(v -> loginWithEmail());
        binding.googleBtn.setOnClickListener(v -> signInWithGoogle());
        binding.signUpTxt.setOnClickListener(v -> navigateToSignUp());
        binding.guestBtn.setOnClickListener(v -> navigateAsGuest());

//        binding.facebookBtn.setOnClickListener(v -> {
//            callFacebook();
//        });
    }

    private void loginWithEmail(){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.loginBtn.setEnabled(false);
        String email = binding.editEmail.getText().toString();
        String password = binding.editpassword.getText().toString();
        loginPresenter.loginWithEmail(email,password);
    }

    private void signInWithGoogle() {
        binding.progressBar.setVisibility(View.VISIBLE);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        googleSignInLauncher.launch(signInIntent); // Launch the sign-in intent
    }

    private void handleGoogleSignInResult(GoogleSignInAccount account) {
        googlePresenter.handleGoogleSignIn(account);
    }

//    private void callFacebook() {
//        LoginManager.getInstance().logInWithReadPermissions(
//                getActivity(),
//                Arrays.asList("email", "public_profile")
//        );
//
//        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                presenter.handleFacebookAccessToken(loginResult.getAccessToken());
//                showInformation("Facebook login successful");
//            }
//
//            @Override
//            public void onCancel() {
//                showInformation("Facebook login canceled");
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                showInformation("Facebook login error: " + error.getMessage());
//            }
//        });
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (callbackManager != null) {
//            callbackManager.onActivityResult(requestCode, resultCode, data);
//        }
//    }


    @Override
    public void showInformation(String msg) {
        binding.progressBar.setVisibility(View.GONE);
        binding.loginBtn.setEnabled(true);
        SnackBarHelper.showCustomSnackBar(requireActivity(),msg, R.color.errorRed, Gravity.TOP);
    }

    @Override
    public void navigateToBase() {
        binding.progressBar.setVisibility(View.GONE);
        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_baseFragment);
    }
    private void navigateToSignUp() {
        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_regiesterFragment);
    }
    private void navigateAsGuest() {
        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_baseFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        loginPresenter = null;
    }

    @Override
    public void onGoogleAuthSuccess() {
        navigateToBase();
    }

    @Override
    public void onGoogleAuthFailure(String message) {
        showInformation(message);
    }
}