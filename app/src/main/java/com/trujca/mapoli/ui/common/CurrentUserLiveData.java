package com.trujca.mapoli.ui.common;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;

public class CurrentUserLiveData extends LiveData<Boolean> implements FirebaseAuth.IdTokenListener {

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
        setValue(auth.getCurrentUser() != null);
    }
}
