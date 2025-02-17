package com.abdok.chefscorner.Data.Repositories.Authentication;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public interface IAuthRepo {
    Task<AuthResult> loginWithEmail(String email, String password);
    FirebaseUser getCurrentUser();
}
