package com.abdok.chefscorner.Data.Repositories.Authentication;

import com.abdok.chefscorner.Data.DataSources.Remote.FireBaseAuthentication.FirebaseAuthDataSource;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

public class AuthRepository implements IAuthRepo {

    private FirebaseAuthDataSource firebaseAuthDataSource;
    private static AuthRepository instance;

    private AuthRepository() {
        firebaseAuthDataSource = FirebaseAuthDataSource.getInstance();
    }

    public static synchronized AuthRepository getInstance() {
        if (instance == null) {
            instance = new AuthRepository();
        }
        return instance;
    }

    @Override
    public Task<AuthResult> loginWithEmail(String email, String password) {
        return firebaseAuthDataSource.getAuth().signInWithEmailAndPassword(email, password);
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return firebaseAuthDataSource.getCurrentUser();
    }

    public Task<AuthResult> signUpWithEmail(String email, String password) {
        return firebaseAuthDataSource.getAuth().createUserWithEmailAndPassword(email, password);
    }

    @Override
    public Task<AuthResult> googleSignIn(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        return firebaseAuthDataSource.getAuth().signInWithCredential(credential);
    }

    @Override
    public Task<AuthResult> signInWithFacebook(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        return firebaseAuthDataSource.getAuth().signInWithCredential(credential);
    }

    @Override
    public Task<Void> updateProfile(String name, String photoUrl) {
        FirebaseUser user = getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        return user.updateProfile(profileUpdates);
    }


}



