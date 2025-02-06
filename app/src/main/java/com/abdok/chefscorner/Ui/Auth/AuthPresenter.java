package com.abdok.chefscorner.Ui.Auth;

import androidx.annotation.NonNull;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

public class AuthPresenter implements IAuthPresenter{

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private IAuthView view;

    public AuthPresenter(IAuthView view ,String  webClientId){
        mAuth = FirebaseAuth.getInstance();
        this.view = view;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(webClientId)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(FirebaseApp.getInstance().getApplicationContext(), gso);
    }


    @Override
    public boolean validateEmail(String email, String password) {
        if(!email.isEmpty() && !password.isEmpty()){
            return true;
        }
        return false;
    }

    @Override
    public void signUpWithEmail(String email, String password,String name , String photoUrl) {
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
    public void loginWithEmail(String email, String password) {
        if(!validateEmail(email,password)){
            view.showInformation("Please fill all fields");
        }
        else{
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                view.navigateToHome();
                            }
                            else{
                                view.showInformation("Failed"+task.getException().getMessage());
                            }

                        }
                    });
        }

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
                            view.navigateToHome();
                        } else {
                            view.showInformation("Failed"+task.getException().getMessage());
                        }
                    });
        }
    }

    @Override
    public void callGoogle() {
        view.callGoogle(mGoogleSignInClient);
    }

    @Override
    public void signInWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> view.navigateToHome())
                .addOnFailureListener(e -> view.showInformation("Failed"+e.getMessage()));
    }

    @Override
    public void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {view.navigateToHome();}
                    else {view.showInformation(task.getException().getMessage());}
                });
    }

    @Override
    public void logout() {
        mAuth.signOut();
    }
}
