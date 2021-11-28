package com.trujca.mapoli.ui.register.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.hadilq.liveevent.LiveEvent;
import com.trujca.mapoli.data.auth.model.RegisterError;
import com.trujca.mapoli.data.auth.model.UserDetails;
import com.trujca.mapoli.data.auth.repository.AuthRepository;
import com.trujca.mapoli.data.util.RepositoryCallback;
import com.trujca.mapoli.ui.base.BaseViewModel;
import com.trujca.mapoli.ui.register.model.RegisterForm;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;

@HiltViewModel
public class RegisterViewModel extends BaseViewModel {

    private final AuthRepository authRepository;

    @Getter
    private final RegisterForm form = new RegisterForm();
    @Getter
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Boolean> signInAction = new LiveEvent<>();
    @Getter
    private final MutableLiveData<RegisterError> signUpError = new LiveEvent<>();
    @Getter
    private final MutableLiveData<UserDetails> signUpSuccess = new LiveEvent<>();

    @Inject
    public RegisterViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void signInAction() {
        signInAction.setValue(true);
    }

    public void signUp() {
        executor.execute(() -> authRepository.register(
                form.getInput().getEmail().get(),
                form.getInput().getPassword().get(),
                new RepositoryCallback<UserDetails, RegisterError>() {

                    @Override
                    public void onLoading(final Boolean loading) {
                        isLoading.postValue(loading);
                        form.setIsProcessing(loading);
                    }

                    @Override
                    public void onSuccess(final UserDetails model) {
                        signUpSuccess.postValue(model);
                    }

                    @Override
                    public void onError(final RegisterError error) {
                        signUpError.postValue(error);
                    }
                })
        );
    }
}