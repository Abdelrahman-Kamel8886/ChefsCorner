package com.abdok.chefscorner.Ui.Auth.Facebook;

import android.net.Uri;

import com.abdok.chefscorner.Data.DataSources.Local.SharedPreference.SharedPreferenceDataSource;
import com.abdok.chefscorner.Models.UserDTO;
import com.abdok.chefscorner.Data.Repositories.Authentication.AuthRepository;
import com.abdok.chefscorner.Data.Repositories.Authentication.IAuthRepo;
import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseUser;

public class FacebookPresenter implements IFacebookPresenter {

    IFacebookView view;
    IAuthRepo authRepo;

    public FacebookPresenter(IFacebookView view) {
        this.view = view;
        authRepo = AuthRepository.getInstance();
    }

    @Override
    public void handleFacebookLogin(AccessToken token) {
        authRepo.signInWithFacebook(token).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                cacheUserData();
            } else {
                view.onFacebookAuthFailure(task.getException().getMessage());
            }
        });
    }

    private void cacheUserData() {
        FirebaseUser user = authRepo.getCurrentUser();
        Uri photoUri = user.getPhotoUrl();
        String photoUrlString = photoUri != null ? photoUri.toString() : null;
        UserDTO userDTO = new UserDTO(user.getUid(),user.getDisplayName(),user.getEmail(),photoUrlString);
        SharedPreferenceDataSource.getInstance().saveUser(userDTO);
        view.onFacebookAuthSuccess();
    }


}
