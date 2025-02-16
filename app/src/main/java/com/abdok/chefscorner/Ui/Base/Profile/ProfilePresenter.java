package com.abdok.chefscorner.Ui.Base.Profile;

import com.abdok.chefscorner.Data.DataSources.Local.SharedPreference.SharedPreferenceDataSource;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class ProfilePresenter implements IProfilePresenter{


    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private IProfileView view;
    private SharedPreferenceDataSource sharedPreferenceDataSource;

    public ProfilePresenter(IProfileView view ,String  webClientId){
        mAuth = FirebaseAuth.getInstance();
        this.view = view;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(webClientId)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(FirebaseApp.getInstance().getApplicationContext(), gso);
        sharedPreferenceDataSource = SharedPreferenceDataSource.getInstance();
    }

    @Override
    public void logout() {
        mAuth.signOut();
        mGoogleSignInClient.signOut();
        clearCache();
    }
    @Override
    public void clearCache() {
        sharedPreferenceDataSource.clearUser();
        view.showInformation("Logged out successfully");
        view.navigateToLogin();
    }
}
