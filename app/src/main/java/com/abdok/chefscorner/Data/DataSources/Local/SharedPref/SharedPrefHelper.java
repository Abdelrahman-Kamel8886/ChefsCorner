package com.abdok.chefscorner.Data.DataSources.Local.SharedPref;

import android.content.Context;
import android.content.SharedPreferences;

import com.abdok.chefscorner.Data.Models.UserDTO;
import com.abdok.chefscorner.Utils.Converter;

public class SharedPrefHelper {

    private static final String PREF_NAME = "ChefCornerPrefs";
    private static final String KEY_USER = "user";

    private static SharedPrefHelper instance;
    private final SharedPreferences sharedPreferences;

    private SharedPrefHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void initSharedPref(Context context) {
        if (instance == null) {
            instance = new SharedPrefHelper(context);
        }
    }

    public static synchronized SharedPrefHelper getInstance() {
        return instance;
    }

    public void saveUser(UserDTO user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String userJson = Converter.userToJson(user);
        editor.putString(KEY_USER, userJson);
        editor.apply();
    }

    public UserDTO getUser() {
        String userJson = sharedPreferences.getString(KEY_USER, null);
        return userJson != null ? Converter.jsonToUser(userJson) : null;
    }

    public void clearUser() {
        sharedPreferences.edit().remove(KEY_USER).apply();
    }

}
