package com.abdok.chefscorner.Data.DataSources.Remote.FireBaseAuthentication;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthDataSource {

    private FirebaseAuth auth;
    private static FirebaseAuthDataSource instance;

    private FirebaseAuthDataSource() {
        auth = FirebaseAuth.getInstance();
    }

    public static synchronized FirebaseAuthDataSource getInstance() {
        if (instance == null) {
            instance = new FirebaseAuthDataSource();
        }
        return instance;
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

}
