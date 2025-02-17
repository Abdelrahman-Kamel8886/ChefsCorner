package com.abdok.chefscorner.Ui.Base.Profile.EditProfile.Presenter;

import android.net.Uri;

public interface IEditProfilePresenter {

    void updateName(String name);
    void updatePhoto(Uri uri);
    void updateBoth(String name, Uri uri);
}
