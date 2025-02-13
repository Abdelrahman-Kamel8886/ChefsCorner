package com.abdok.chefscorner.Ui.Auth;

import com.facebook.AccessToken;

public interface IAuthPresenter {

    boolean validateEmail(String email , String password);
    void loginWithEmail(String email, String password);
    void callGoogle();
    void signInWithGoogle(String idToken);
    void handleFacebookAccessToken(AccessToken token);
    void cacheUserData();
}
