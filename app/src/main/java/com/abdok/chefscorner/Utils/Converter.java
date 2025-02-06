package com.abdok.chefscorner.Utils;

import com.abdok.chefscorner.Models.UserDTO;
import com.google.gson.Gson;

public class Converter {

    private static Gson gson = new Gson();

    public static String userToJson(UserDTO user) {
        return gson.toJson(user);
    }

    public static UserDTO jsonToUser(String json) {
        return gson.fromJson(json, UserDTO.class);
    }
}
