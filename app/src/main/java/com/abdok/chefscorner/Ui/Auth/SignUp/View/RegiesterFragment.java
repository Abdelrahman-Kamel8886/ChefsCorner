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

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Ui.Auth.Facebook.FacebookPresenter;
import com.abdok.chefscorner.Ui.Auth.Facebook.IFacebookPresenter;
import com.abdok.chefscorner.Ui.Auth.Facebook.IFacebookView;
import com.abdok.chefscorner.Ui.Auth.Google.GooglePresenter;
import com.abdok.chefscorner.Ui.Auth.Google.IGooglePresenter;
import com.abdok.chefscorner.Ui.Auth.Google.IGoogleView;
import com.abdok.chefscorner.Ui.Auth.SignUp.Presenter.ISignUpPresenter;
import com.abdok.chefscorner.Ui.Auth.SignUp.Presenter.SignUpPresenter;
import com.abdok.chefscorner.Utils.Helpers.SnackBarHelper;
import com.abdok.chefscorner.databinding.FragmentLoginBinding;
import com.abdok.chefscorner.databinding.FragmentRegiesterBinding;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;


public class RegiesterFragment extends Fragment implements ISignUpView, IGoogleView , IFacebookView {


    FragmentRegiesterBinding binding;

    //SignUp
    ISignUpPresenter presenter;

    //Google
    private static final int RC_SIGN_IN = 123;
    IGooglePresenter googlePresenter;
    private GoogleSignInClient mGoogleSignInClient;
    private ActivityResultLauncher<Intent> googleSignInLauncher;

    //Facebook
    private CallbackManager callbackManager;
    IFacebookPresenter facebookPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_regiester, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callbackManager = CallbackManager.Factory.create();

        binding = FragmentRegiesterBinding.bind(view);

        presenter = new SignUpPresenter(this);
        googlePresenter = new GooglePresenter(this);
        facebookPresenter = new FacebookPresenter(this);


        initializeGoogle();
        initializeFacebook();


        onClicks();
    }

    private void initializeFacebook() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("FacebookLogin", "Success: " + loginResult);
                AccessToken accessToken = loginResult.getAccessToken();
                facebookPresenter.handleFacebookLogin(accessToken);
            }

            @Override
            public void onCancel() {
                showInformation("Login canceled");
            }

            @Override
            public void onError(FacebookException error) {
                showInformation("Login failed: " + error.getMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void startFacebookLogin() {
        binding.progressBar.setVisibility(View.VISIBLE);
        LoginManager.getInstance().logInWithReadPermissions(
                RegiesterFragment.this,
                Arrays.asList("email", "public_profile")
        );
    }

    private void initializeGoogle() {
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
    
    private void onClicks() {
        binding.backBtn.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        binding.loginTxt.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        binding.signBtn.setOnClickListener(v -> validation());
        binding.googleBtn.setOnClickListener(v -> signInWithGoogle());
        binding.facebookBtn.setOnClickListener(v -> startFacebookLogin());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }

    private void validation() {
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
    public void onFacebookAuthSuccess() {
        navigateToBase();
    }

    @Override
    public void onFacebookAuthFailure(String message) {
        showInformation(message);
    }

    @Override
    public void navigateToBase() {
        binding.progressBar.setVisibility(View.GONE);
        Navigation.findNavController(requireView()).navigate(R.id.action_regiesterFragment_to_baseFragment);
    }
}