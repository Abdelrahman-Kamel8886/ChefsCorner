package com.abdok.chefscorner.Ui.Auth.SignUp;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.abdok.chefscorner.Local.SharedPref.SharedPrefHelper;
import com.abdok.chefscorner.Models.UserDTO;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpPresenter implements ISignUpPresenter{

    private FirebaseAuth mAuth;
    private ISignUpView view;

    public SignUpPresenter(ISignUpView view){
        mAuth = FirebaseAuth.getInstance();
        this.view = view;
    }

    @Override
    public void validateData(String name, String email, String password, String confirmPassword) {
        if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()){
            if(password.equals(confirmPassword)){
                signUpWithEmail(email,password,name,"none");
            }
            else{
                view.showInformation("Passwords do not match");
            }
        }
        else{
            view.showInformation("Please fill all fields");
        }
    }

    @Override
    public void signUpWithEmail(String email, String password, String name, String photoUrl) {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        updateProfile(name,photoUrl);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       view.showInformation("Failed : "+e.getMessage());
                    }
                });
    }

    @Override
    public void updateProfile(String name, String photoUrl) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser updatedUser = FirebaseAuth.getInstance().getCurrentUser();
                            Uri photoUri = updatedUser.getPhotoUrl();
                            String photoUrlString = photoUri != null ? photoUri.toString() : null;
                            UserDTO userDTO = new UserDTO(updatedUser.getUid(),updatedUser.getDisplayName(),updatedUser.getEmail(),photoUrlString);
                            cacheUserData(userDTO);

                        } else {
                           view.showInformation("Failed"+task.getException().getMessage());
                        }
                    });
        }
    }

    @Override
    public void cacheUserData(UserDTO user) {
        SharedPrefHelper.getInstance().saveUser(user);
        view.navigateToBase();
    }
}
