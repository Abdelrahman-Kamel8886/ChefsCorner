package com.abdok.chefscorner.Ui.Base.Profile.View;

import com.abdok.chefscorner.Models.UserDTO;

public interface IProfileView{

    void showUserData(UserDTO user);
    void navigateToLogin();
    void showInformation(String msg);

    void toggleHistoryBtn(boolean isHistory);

}
