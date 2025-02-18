package com.abdok.chefscorner.Ui.Auth.SignUp.Presenter;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.abdok.chefscorner.Data.DataSources.Local.SharedPreference.SharedPreferenceDataSource;
import com.abdok.chefscorner.Models.UserDTO;
import com.abdok.chefscorner.Data.Repositories.Authentication.AuthRepository;
import com.abdok.chefscorner.Data.Repositories.Authentication.IAuthRepo;
import com.abdok.chefscorner.Ui.Auth.SignUp.View.ISignUpView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpPresenter implements ISignUpPresenter {

    private IAuthRepo authRepo;
    private ISignUpView view;

    public SignUpPresenter(ISignUpView view) {
        this.view = view;
        this.authRepo = AuthRepository.getInstance();
    }

    @Override
    public void validateData(String name, String email, String password, String confirmPassword) {
        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
            if (password.equals(confirmPassword)) {
                signUpWithEmail(email, password, name, "none");
            } else {
                view.showInformation("Passwords do not match");
            }
        } else {
            view.showInformation("Please fill all fields");
        }
    }

    @Override
    public void signUpWithEmail(String email, String password, String name, String photoUrl) {
        authRepo.signUpWithEmail(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        updateProfile(name, photoUrl);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        view.showInformation("Failed : " + e.getMessage());
                    }
                });
    }

    @Override
    public void updateProfile(String name, String photoUrl) {
        authRepo.updateProfile(name, photoUrl)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser updatedUser = FirebaseAuth.getInstance().getCurrentUser();
                        Uri photoUri = updatedUser.getPhotoUrl();
                        String photoUrlString = photoUri != null ? photoUri.toString() : null;
                        UserDTO userDTO = new UserDTO(updatedUser.getUid(), updatedUser.getDisplayName(), updatedUser.getEmail(), photoUrlString);
                        cacheUserData(userDTO);

                    } else {
                        view.showInformation("Failed" + task.getException().getMessage());
                    }
                });
    }

    @Override
    public void cacheUserData(UserDTO user) {
        SharedPreferenceDataSource.getInstance().saveUser(user);
        view.navigateToBase();
    }
}
