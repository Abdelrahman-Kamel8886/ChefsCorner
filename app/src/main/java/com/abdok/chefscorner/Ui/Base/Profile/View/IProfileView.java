package com.abdok.chefscorner.Ui.Base.Profile.View;

import com.abdok.chefscorner.Data.Models.UserDTO;

public interface IProfileView{

    void showUserData(UserDTO user);
    void navigateToLogin();
    void showInformation(String msg);

}
