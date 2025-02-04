package com.abdok.chefscorner.Ui.Auth;

import com.facebook.AccessToken;

public interface IAuthPresenter {

    boolean validateEmail(String email , String password);

    void signUpWithEmail(String email, String password,String name , String photoUrl);
    void loginWithEmail(String email, String password);
    void updateProfile(String name , String photoUrl);
    void callGoogle();
    void signInWithGoogle(String idToken);
    void handleFacebookAccessToken(AccessToken token);

    void logout();
}
