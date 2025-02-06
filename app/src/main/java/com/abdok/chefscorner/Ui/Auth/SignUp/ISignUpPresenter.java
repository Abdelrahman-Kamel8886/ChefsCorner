package com.abdok.chefscorner.Ui.Auth.SignUp;

public interface ISignUpPresenter {

    void signUpWithEmail(String email, String password,String name , String photoUrl);
    void validateData(String name ,String email , String password , String confirmPassword);
    void updateProfile(String name , String photoUrl);

}
