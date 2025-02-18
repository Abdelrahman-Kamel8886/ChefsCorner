package com.abdok.chefscorner.Ui.Base.Profile.EditProfile.Presenter;

import android.net.Uri;

import com.abdok.chefscorner.Data.DataSources.Local.SharedPreference.SharedPreferenceDataSource;
import com.abdok.chefscorner.Models.UserDTO;
import com.abdok.chefscorner.Ui.Base.Profile.EditProfile.View.IEditProfileView;
import com.abdok.chefscorner.Utils.SharedModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class EditProfilePresenter implements IEditProfilePresenter{

    IEditProfileView view;
    private FirebaseAuth mAuth;
    private SharedPreferenceDataSource sharedPreferenceDataSource;

    public EditProfilePresenter(IEditProfileView view){
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
        sharedPreferenceDataSource = SharedPreferenceDataSource.getInstance();
    }

    @Override
    public void updateName(String name) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build();
            updateFirebaseProfile(user,profileUpdates);
        }
    }

    @Override
    public void updatePhoto(Uri uri) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(uri)
                    .build();
            updateFirebaseProfile(user,profileUpdates);
        }
    }

    @Override
    public void updateBoth(String name, Uri uri) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(uri)
                    .setDisplayName(name)
                    .build();
            updateFirebaseProfile(user,profileUpdates);
        }

    }
    private void updateFirebaseProfile(FirebaseUser user ,UserProfileChangeRequest profileUpdates){
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser updatedUser = FirebaseAuth.getInstance().getCurrentUser();
                        Uri photoUri = updatedUser.getPhotoUrl();
                        String photoUrlString = photoUri != null ? photoUri.toString() : null;
                        UserDTO userDTO = new UserDTO(updatedUser.getUid(),updatedUser.getDisplayName(),updatedUser.getEmail(),photoUrlString);
                        cacheUserData(userDTO);

                    } else {
                        view.onError("Failed to update your profile");
                    }
                });

    }

    private void cacheUserData(UserDTO user) {
        SharedPreferenceDataSource.getInstance().saveUser(user);
        SharedModel.setUser(user);
        view.onSuccess("Profile updated successfully");
    }

}
