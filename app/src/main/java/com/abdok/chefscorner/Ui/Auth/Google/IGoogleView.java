package com.abdok.chefscorner.Ui.Auth.Google;

public interface IGoogleView {
    void onGoogleAuthSuccess();
    void onGoogleAuthFailure(String message);
}
