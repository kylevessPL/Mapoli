package com.trujca.mapoli.data.auth.repository;

import com.trujca.mapoli.data.auth.exception.UserNotLoggedInException;
import com.trujca.mapoli.data.auth.model.UserDetails;
import com.trujca.mapoli.data.util.RepositoryCallback;

public interface AuthRepository {
    void loginWithEmail(String email, String password, RepositoryCallback<UserDetails> callback);

    void loginWithGoogle(String token, RepositoryCallback<UserDetails> callback);

    void register(String email, String password, RepositoryCallback<UserDetails> callback);

    void logout(RepositoryCallback<Void> callback);

    UserDetails getCurrentUserDetails() throws UserNotLoggedInException;
}
