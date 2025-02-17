package com.abdok.chefscorner.Data.Repositories.Authentication;

import com.abdok.chefscorner.Data.DataSources.Remote.FireBaseAuthentication.FirebaseAuthDataSource;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class AuthRepository implements IAuthRepo{

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




}
