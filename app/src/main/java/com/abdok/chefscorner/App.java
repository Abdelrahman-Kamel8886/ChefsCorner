package com.abdok.chefscorner;

import android.app.Application;

import com.abdok.chefscorner.Local.SharedPref.SharedPrefHelper;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPrefHelper.initSharedPref(this);
    }
}
