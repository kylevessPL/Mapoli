package com.trujca.mapoli.ui.login.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.hadilq.liveevent.LiveEvent;
import com.trujca.mapoli.data.auth.model.LoginError;
import com.trujca.mapoli.data.auth.model.UserDetails;
import com.trujca.mapoli.data.auth.repository.AuthRepository;
import com.trujca.mapoli.data.util.RepositoryCallback;
import com.trujca.mapoli.ui.base.BaseViewModel;
import com.trujca.mapoli.ui.login.model.LoginForm;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;

@HiltViewModel
public class LoginViewModel extends BaseViewModel {

    private final AuthRepository authRepository;

    @Getter
    private final LoginForm form = new LoginForm();
    @Getter
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Boolean> signUpAction = new LiveEvent<>();
    @Getter
    private final MutableLiveData<LoginError> signInError = new LiveEvent<>();

    @Inject
    public LoginViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void signUpAction() {
        signUpAction.setValue(true);
    }

    public void signInWithEmail() {
        executor.execute(() -> authRepository.loginWithEmail(
                form.getInput().getEmail().get(),
                form.getInput().getPassword().get(),
                new RepositoryCallback<UserDetails, LoginError>() {

                    @Override
                    public void onLoading(final Boolean loading) {
                        isLoading.postValue(loading);
                    }

                    @Override
                    public void onSuccess(final UserDetails model) {
                    }

                    @Override
                    public void onError(final LoginError error) {
                        signInError.postValue(error);
                    }
                })
        );
    }

    public void signInWithGoogle(String token) {
        executor.execute(() -> authRepository.loginWithGoogle(token, new RepositoryCallback<UserDetails, LoginError>() {

            @Override
            public void onLoading(final Boolean loading) {
                isLoading.postValue(loading);
            }

            @Override
            public void onSuccess(final UserDetails model) {
            }

            @Override
            public void onError(final LoginError error) {
                signInError.postValue(error);
            }
        }));
    }
}