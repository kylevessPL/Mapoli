package com.trujca.mapoli.data.auth.repository;

import static java.util.Objects.requireNonNull;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.trujca.mapoli.data.auth.exception.UserNotLoggedInException;
import com.trujca.mapoli.data.auth.model.LoginError;
import com.trujca.mapoli.data.auth.model.RegisterError;
import com.trujca.mapoli.data.auth.model.UserDetails;
import com.trujca.mapoli.data.util.RepositoryCallback;
import com.trujca.mapoli.util.AppUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class FirebaseAuthRespository implements AuthRepository {

    private static final String TAG = FirebaseAuthRespository.class.getSimpleName();

    private final FirebaseAuth auth;
    private final GoogleSignInClient googleSignInClient;
    private final FirebaseFirestore firebase;

    @Inject
    public FirebaseAuthRespository(FirebaseAuth auth, GoogleSignInClient googleSignInClient) {
        this.auth = auth;
        this.googleSignInClient = googleSignInClient;
        this.firebase = FirebaseFirestore.getInstance();
    }

    @Override
    public void loginWithEmail(final String email, final String password, final RepositoryCallback<UserDetails, LoginError> callback) {
        callback.onLoading(true);
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        callback.onSuccess(AppUtils.toUserDetails(requireNonNull(user)));

                        Log.w(TAG, "loginWithEmail:success");
                    } else {
                        Exception ex = task.getException();
                        if (ex instanceof FirebaseAuthException) {
                            callback.onError(LoginError.INVALID_CREDENTIALS);
                        } else {
                            callback.onError(LoginError.INTERNAL_ERROR);
                        }
                        Log.w(TAG, "loginWithEmail:failure", ex);
                    }
                    callback.onLoading(false);
                });
    }

    @Override
    public void loginWithGoogle(final String token, final RepositoryCallback<UserDetails, LoginError> callback) {
        callback.onLoading(true);
        AuthCredential credential = GoogleAuthProvider.getCredential(token, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        callback.onSuccess(AppUtils.toUserDetails(requireNonNull(user)));

                        /*
                        Map<String,String> newUser = new HashMap<>();
                        newUser.put("category1","");
                        firebase.collection("users").document(user.getUid())
                                .set(newUser)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully written!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error writing document", e);
                                    }
                                });

                                //TODO: google login mi nie działa to wole tego nie próbować
                         */
                        Log.w(TAG, "loginWithGoogle:success");
                    } else {
                        Exception ex = task.getException();
                        if (ex instanceof FirebaseAuthException) {
                            callback.onError(LoginError.INVALID_CREDENTIALS);
                        } else {
                            callback.onError(LoginError.INTERNAL_ERROR);
                        }
                        Log.w(TAG, "loginWithGoogle:failure", ex);
                    }
                    callback.onLoading(false);
                });
    }

    @Override
    public void register(final String email, final String password, final RepositoryCallback<UserDetails, RegisterError> callback) {
        callback.onLoading(true);
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        callback.onSuccess(AppUtils.toUserDetails(requireNonNull(user)));

                        Map<String, String> newUser = new HashMap<>();
                        newUser.put("name", ""); //dummy cause document has to have any data
                        firebase.collection("users").document(user.getUid()).collection("categories").document("category1")
                                .set(newUser)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully written!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error writing document", e);
                                    }
                                });

                        Log.w(TAG, "register:success");
                    } else {
                        Exception ex = task.getException();
                        if (ex instanceof FirebaseAuthException) {
                            callback.onError(RegisterError.EMAIL_IN_USE);
                        } else {
                            callback.onError(RegisterError.INTERNAL_ERROR);
                        }
                        Log.w(TAG, "register:failure", ex);
                    }
                    callback.onLoading(false);
                });
    }

    @Override
    public void logout(final RepositoryCallback<Void, Void> callback) {
        callback.onLoading(true);
        auth.signOut();
        googleSignInClient.signOut()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess(null);
                        Log.w(TAG, "logout:success");
                    } else {
                        Exception ex = requireNonNull(task.getException());
                        callback.onError(null);
                        Log.w(TAG, "logout:failure", ex);
                    }
                    callback.onLoading(false);
                });
    }

    @Override
    @NonNull
    public UserDetails getCurrentUserDetails() throws UserNotLoggedInException {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            return AppUtils.toUserDetails(user);
        }
        throw new UserNotLoggedInException();
    }
}
