package com.example.eduforum.activity.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.eduforum.activity.util.FlagsList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginRepository {

    protected FirebaseAuth mAuth;

    public LoginRepository() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void login(String email, String password, ILoginCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(FlagsList.DEBUG_LOGIN_FLAG, "signInWithEmail:success");
                            FirebaseUser mUser = mAuth.getCurrentUser();
                            assert mUser != null;
                            if (mUser.isEmailVerified()) {
                                // Navigation to community
                                callback.onLoginSuccess();
                            } else {
                                Log.w(FlagsList.DEBUG_LOGIN_FLAG, "signInWithEmail:success but email is not verified");
                                callback.onLoginFailed(FlagsList.ERROR_LOGIN_EMAIL_NOT_VERIFIED);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(FlagsList.DEBUG_LOGIN_FLAG, "signInWithEmail:failure", task.getException());
                            callback.onLoginFailed(FlagsList.ERROR_LOGIN_EMAIL_NOT_VERIFIED);
                        }
                    }
                });
    }
}
