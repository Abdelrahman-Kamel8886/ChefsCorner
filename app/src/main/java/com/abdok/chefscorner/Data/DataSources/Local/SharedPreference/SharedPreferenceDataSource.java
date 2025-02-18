package com.abdok.chefscorner.Data.DataSources.Local.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.abdok.chefscorner.Models.UserDTO;
import com.abdok.chefscorner.Utils.Helpers.Converter;

public class SharedPreferenceDataSource {

    private static final String PREF_NAME = "ChefCornerPrefs";
    private static final String KEY_USER = "user";

    private static SharedPreferenceDataSource instance;
    private final SharedPreferences sharedPreferences;

    private SharedPreferenceDataSource(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void initSharedPref(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceDataSource(context);
        }
    }

    public static synchronized SharedPreferenceDataSource getInstance() {
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
