package com.abdok.chefscorner.Utils;

import com.abdok.chefscorner.Models.UserDTO;

public class SharedModel {

    private static UserDTO currentUser;

    public static UserDTO getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(UserDTO currentUser) {
        SharedModel.currentUser = currentUser;
    }
}
