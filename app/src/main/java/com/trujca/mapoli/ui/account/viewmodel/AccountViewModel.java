package com.trujca.mapoli.ui.account.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.hadilq.liveevent.LiveEvent;
import com.trujca.mapoli.data.auth.exception.UserNotLoggedInException;
import com.trujca.mapoli.data.auth.model.UserDetails;
import com.trujca.mapoli.data.auth.repository.AuthRepository;
import com.trujca.mapoli.data.util.RepositoryCallback;
import com.trujca.mapoli.ui.base.BaseViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;
import lombok.SneakyThrows;

@HiltViewModel
public class AccountViewModel extends BaseViewModel {

    private final AuthRepository authRepository;

    @Getter
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Boolean> signOutAction = new LiveEvent<>();
    @Getter
    private final MutableLiveData<Boolean> signOutResult = new LiveEvent<>();
    @Getter
    private final MutableLiveData<UserDetails> userDetails = new MutableLiveData<>();

    @Inject
    public AccountViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
        fetchUserDetails();
    }

    public void signOutAction() {
        signOutAction.setValue(true);
    }

    public void signOut() {
        executor.execute(() -> authRepository.logout(new RepositoryCallback<Void, Void>() {

            @Override
            public void onLoading(final Boolean loading) {
                isLoading.postValue(loading);
            }

            @Override
            public void onSuccess(final Void model) {
                signOutResult.postValue(true);
            }

            @Override
            public void onError(final Void ex) {
                signOutResult.postValue(false);
            }
        }));
    }

    @SneakyThrows
    private void fetchUserDetails() {
        UserDetails userDetails = authRepository.getCurrentUserDetails();
        this.userDetails.setValue(userDetails);
    }
}