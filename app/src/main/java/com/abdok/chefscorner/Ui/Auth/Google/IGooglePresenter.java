package com.abdok.chefscorner.Ui.Auth.Google;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface IGooglePresenter {
    void handleGoogleSignIn(GoogleSignInAccount account);
}
