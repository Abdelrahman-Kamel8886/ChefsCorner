package com.abdok.chefscorner.Ui.Auth.SignUp.Presenter;

import com.abdok.chefscorner.Data.Models.UserDTO;

public interface ISignUpPresenter {

    void signUpWithEmail(String email, String password,String name , String photoUrl);
    void validateData(String name ,String email , String password , String confirmPassword);
    void updateProfile(String name , String photoUrl);
    void cacheUserData(UserDTO user);
}
