package com.abdok.chefscorner.Ui.Auth.Login.Presenter;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.abdok.chefscorner.Data.DataSources.Local.SharedPreference.SharedPreferenceDataSource;
import com.abdok.chefscorner.Models.UserDTO;
import com.abdok.chefscorner.Data.Repositories.Authentication.AuthRepository;
import com.abdok.chefscorner.Data.Repositories.Authentication.IAuthRepo;
import com.abdok.chefscorner.Ui.Auth.Login.View.ILoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenter implements ILoginPresenter{

    ILoginView view;
    IAuthRepo authRepo;

    public LoginPresenter(ILoginView view){
        this.view = view;
        authRepo = AuthRepository.getInstance();
    }

    @Override
    public boolean validateEmail(String email, String password) {
        if(!email.isEmpty() && !password.isEmpty()){
            return true;
        }
        return false;
    }

    @Override
    public void loginWithEmail(String email, String password) {
        if(!validateEmail(email,password)){
           view.showInformation("Please fill all fields");
        }
        else{

            authRepo.loginWithEmail(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        cacheUserData();
                    }
                    else{
                       view.showInformation("Failed"+task.getException().getMessage());
                    }

                }
            });

        }
    }

    @Override
    public void cacheUserData() {
        FirebaseUser user = authRepo.getCurrentUser();
        Uri photoUri = user.getPhotoUrl();
        String photoUrlString = photoUri != null ? photoUri.toString() : null;
        UserDTO userDTO = new UserDTO(user.getUid(),user.getDisplayName(),user.getEmail(),photoUrlString);
        SharedPreferenceDataSource.getInstance().saveUser(userDTO);
        view.navigateToBase();
    }
}
