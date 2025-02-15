package com.abdok.chefscorner.Ui.Auth.Login;

import static android.app.Activity.RESULT_OK;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abdok.chefscorner.R;
import com.abdok.chefscorner.databinding.FragmentLoginBinding;
import com.abdok.chefscorner.Ui.Auth.AuthPresenter;
import com.abdok.chefscorner.Ui.Auth.IAuthPresenter;
import com.abdok.chefscorner.Ui.Auth.IAuthView;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;


public class LoginFragment extends Fragment implements IAuthView {

    IAuthPresenter presenter;
    FragmentLoginBinding binding;

    private CallbackManager callbackManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentLoginBinding.bind(view);
        presenter = new AuthPresenter(this , getContext().getString(R.string.default_web_client_id));
        callbackManager = CallbackManager.Factory.create();
        onClicks();
    }

    private void onClicks(){
        binding.loginBtn.setOnClickListener(v -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.loginBtn.setEnabled(false);
            String email = binding.editEmail.getText().toString();
            String password = binding.editpassword.getText().toString();
            presenter.loginWithEmail(email,password);

        });
        binding.googleBtn.setOnClickListener(v -> {
            presenter.callGoogle();
        });
        binding.facebookBtn.setOnClickListener(v -> {
            callFacebook();
        });
        binding.signUpTxt.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_regiesterFragment);
        });

        binding.guestBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_baseFragment);
        });

    }

    private void callFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(
                getActivity(),
                Arrays.asList("email", "public_profile")
        );

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                presenter.handleFacebookAccessToken(loginResult.getAccessToken());
                showInformation("Facebook login successful");
            }

            @Override
            public void onCancel() {
                showInformation("Facebook login canceled");
            }

            @Override
            public void onError(FacebookException error) {
                showInformation("Facebook login error: " + error.getMessage());
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


    @Override
    public void showInformation(String msg) {
        binding.progressBar.setVisibility(View.GONE);
        binding.loginBtn.setEnabled(true);
        Snackbar snackbar = Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.BLACK);
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(16);
        snackbar.show();

    }



    @Override
    public void callGoogle(GoogleSignInClient mGoogleSignInClient) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        googleSignInLauncher.launch(signInIntent);
    }

    @Override
    public void navigateToBase() {
        binding.progressBar.setVisibility(View.GONE);
        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_baseFragment);
    }

    private final ActivityResultLauncher<Intent> googleSignInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    try {
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        presenter.signInWithGoogle(account.getIdToken());
                    } catch (ApiException e) {
                        Log.e("GoogleSignIn", "Sign-in failed: " + e.getMessage());
                    }
                }
            }
    );

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        presenter = null;
    }
}