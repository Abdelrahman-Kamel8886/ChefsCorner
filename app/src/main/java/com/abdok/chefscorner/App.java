package com.abdok.chefscorner;

import android.app.Application;

import com.abdok.chefscorner.Data.DataSources.Local.Room.LocalDataBase;
import com.abdok.chefscorner.Data.DataSources.Local.SharedPref.SharedPrefHelper;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPrefHelper.initSharedPref(this);
        LocalDataBase.initLocalDataBase(this);
    }
}
