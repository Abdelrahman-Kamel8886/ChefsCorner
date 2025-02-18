package com.abdok.chefscorner.Ui.Base.ProfileEdit.Presenter;

import android.net.Uri;

public interface IEditProfilePresenter {

    void updateName(String name);
    void updatePhoto(Uri uri);
    void updateBoth(String name, Uri uri);
}
