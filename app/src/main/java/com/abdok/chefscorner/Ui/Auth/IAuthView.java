package com.abdok.chefscorner.Ui.Auth;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public interface IAuthView {

    void showInformation(String msg);
    void callGoogle(GoogleSignInClient mGoogleSignInClient);
    void navigateToHome();

}
