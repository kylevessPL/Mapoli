package com.trujca.mapoli.data.auth.repository;

import com.trujca.mapoli.data.auth.exception.UserNotLoggedInException;
import com.trujca.mapoli.data.auth.model.LoginError;
import com.trujca.mapoli.data.auth.model.RegisterError;
import com.trujca.mapoli.data.auth.model.UserDetails;
import com.trujca.mapoli.data.util.RepositoryCallback;

public interface AuthRepository {
    void loginWithEmail(String email, String password, RepositoryCallback<UserDetails, LoginError> callback);

    void loginWithGoogle(String token, RepositoryCallback<UserDetails, LoginError> callback);

    void register(String email, String password, RepositoryCallback<UserDetails, RegisterError> callback);

    void logout(RepositoryCallback<Void, Void> callback);

    UserDetails getCurrentUserDetails() throws UserNotLoggedInException;
}
