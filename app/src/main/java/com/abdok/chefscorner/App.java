package com.abdok.chefscorner;

import android.app.Application;

import com.abdok.chefscorner.Data.DataSources.Local.Room.LocalDataBase;
import com.abdok.chefscorner.Data.DataSources.Local.SharedPreference.SharedPreferenceDataSource;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferenceDataSource.initSharedPref(this);
        LocalDataBase.initLocalDataBase(this);
    }
}
