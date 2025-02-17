package com.abdok.chefscorner.Ui.Auth.Facebook;

import com.facebook.AccessToken;

public interface IFacebookPresenter {

    void handleFacebookLogin(AccessToken token);
}
