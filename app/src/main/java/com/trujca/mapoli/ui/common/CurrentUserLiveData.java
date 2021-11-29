package com.trujca.mapoli.ui.common;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.trujca.mapoli.data.auth.model.UserDetails;
import com.trujca.mapoli.util.AppUtils;

public class CurrentUserLiveData extends LiveData<UserDetails> implements FirebaseAuth.IdTokenListener {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onActive() {
        super.onActive();
        updateUser(auth);
        auth.addIdTokenListener(this);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        auth.removeIdTokenListener(this);
    }

    @Override
    public void onIdTokenChanged(@NonNull final FirebaseAuth firebaseAuth) {
        updateUser(auth);
    }

    private void updateUser(FirebaseAuth auth) {
        UserDetails userDetails = null;
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            userDetails = AppUtils.toUserDetails(user);
        }
        setValue(userDetails);
    }
}
