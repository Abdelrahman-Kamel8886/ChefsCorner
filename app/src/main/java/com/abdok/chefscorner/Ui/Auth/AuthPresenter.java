package com.abdok.chefscorner.Ui.Auth;

import androidx.annotation.NonNull;

import com.abdok.chefscorner.Local.SharedPref.SharedPrefHelper;
import com.abdok.chefscorner.Models.UserDTO;
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
    public void callGoogle() {
        view.callGoogle(mGoogleSignInClient);
    }

    @Override
    public void signInWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> cacheUserData())
                .addOnFailureListener(e -> view.showInformation("Failed"+e.getMessage()));
    }

    @Override
    public void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {cacheUserData();}
                    else {view.showInformation(task.getException().getMessage());}
                });
    }

    @Override
    public void cacheUserData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserDTO userDTO = new UserDTO(user.getUid(),user.getDisplayName(),user.getEmail(),user.getPhotoUrl().toString());
        SharedPrefHelper.getInstance().saveUser(userDTO);
        view.navigateToHome();
    }

    @Override
    public void logout() {
        mAuth.signOut();
    }
}
