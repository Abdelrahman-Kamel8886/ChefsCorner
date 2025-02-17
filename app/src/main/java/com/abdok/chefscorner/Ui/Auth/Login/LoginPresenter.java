package com.abdok.chefscorner.Ui.Auth.Login;

import androidx.annotation.NonNull;

import com.abdok.chefscorner.Data.Repositories.Authentication.AuthRepository;
import com.abdok.chefscorner.Data.Repositories.Authentication.IAuthRepo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

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

    }
}
