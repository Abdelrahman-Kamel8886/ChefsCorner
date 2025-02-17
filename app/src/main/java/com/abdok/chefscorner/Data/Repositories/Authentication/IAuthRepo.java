package com.abdok.chefscorner.Data.Repositories.Authentication;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public interface IAuthRepo {

    //Login
    Task<AuthResult> loginWithEmail(String email, String password);
    FirebaseUser getCurrentUser();

    //Register
    Task<AuthResult> signUpWithEmail(String email, String password);
    Task<Void> updateProfile(String name, String photoUrl);

    //Google
    Task<AuthResult> googleSignIn(GoogleSignInAccount account);

    //Facebook
    Task<AuthResult> signInWithFacebook(AccessToken token);
}
