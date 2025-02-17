package com.abdok.chefscorner.Data.Repositories.Authentication;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface IAuthRepo {
    Task<AuthResult> loginWithEmail(String email, String password);
}
