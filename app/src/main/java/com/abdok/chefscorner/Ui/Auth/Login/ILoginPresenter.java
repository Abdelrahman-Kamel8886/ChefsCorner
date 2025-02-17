package com.abdok.chefscorner.Ui.Auth.Login;

import com.facebook.AccessToken;

public interface ILoginPresenter {
    boolean validateEmail(String email , String password);
    void loginWithEmail(String email, String password);
    void cacheUserData();
}
