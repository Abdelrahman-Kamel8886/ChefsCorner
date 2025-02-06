package com.abdok.chefscorner.Ui.Auth.SignUp;

import androidx.annotation.NonNull;

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
    public boolean validateEmail(String name, String email, String password, String confirmPassword) {
        return false;
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
                       // view.showToast("Failed : "+e.getMessage());
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
                           // view.navigateToHome();
                        } else {
                           // view.showToast("Failed"+task.getException().getMessage());
                        }
                    });
        }
    }
}
