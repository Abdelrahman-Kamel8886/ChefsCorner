package com.abdok.chefscorner.Ui.Auth.Google;

import android.net.Uri;

import com.abdok.chefscorner.Data.DataSources.Local.SharedPreference.SharedPreferenceDataSource;
import com.abdok.chefscorner.Data.Models.UserDTO;
import com.abdok.chefscorner.Data.Repositories.Authentication.AuthRepository;
import com.abdok.chefscorner.Data.Repositories.Authentication.IAuthRepo;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;

public class GooglePresenter implements IGooglePresenter {


    private IGoogleView view;
    private IAuthRepo authRepo;

    public GooglePresenter(IGoogleView view) {
        this.view = view;
        this.authRepo = AuthRepository.getInstance();
    }

    @Override
    public void handleGoogleSignIn(GoogleSignInAccount account) {
        authRepo.googleSignIn(account).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                cacheUserData();
            } else {
                view.onGoogleAuthFailure(task.getException().getMessage());
            }
        });
    }

    public void cacheUserData() {
        FirebaseUser user = authRepo.getCurrentUser();
        Uri photoUri = user.getPhotoUrl();
        String photoUrlString = photoUri != null ? photoUri.toString() : null;
        UserDTO userDTO = new UserDTO(user.getUid(),user.getDisplayName(),user.getEmail(),photoUrlString);
        SharedPreferenceDataSource.getInstance().saveUser(userDTO);
        view.onGoogleAuthSuccess();
    }
}
