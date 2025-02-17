package com.abdok.chefscorner.Ui.Auth.Login;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public interface ILoginView {

    void showInformation(String msg);
    void navigateToBase();
}
