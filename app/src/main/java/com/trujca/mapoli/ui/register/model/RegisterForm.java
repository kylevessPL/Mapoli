package com.trujca.mapoli.ui.register.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.trujca.mapoli.BR;
import com.trujca.mapoli.util.ValidationUtils;

import lombok.Getter;

@Getter
public class RegisterForm extends BaseObservable {

    private final InputField input = new InputField();
    private final ErrorField error = new ErrorField();

    private Boolean processing = false;

    public void setIsProcessing(Boolean processing) {
        this.processing = processing;
        notifyPropertyChanged(BR.registerFormValid);
    }

    @Bindable
    public boolean isRegisterFormValid() {
        return !processing && validateEmail(false) && validatePassword(false) && validatePasswordConfirm(false);
    }

    public Boolean validateEmail(Boolean showError) {
        Integer message = ValidationUtils.emailValidationMessage(input.email.get());
        if (message != null && !showError) {
            error.email.set(message);
        }
        return message == null;
    }

    public Boolean validateEmail() {
        return ValidationUtils.emailValidationMessage(input.email.get()) == null;
    }

    public Boolean validatePassword(Boolean showError) {
        initValidatePassword();
        Integer message = ValidationUtils.passwordValidationMessage(input.password.get(), true);
        if (message != null && !showError) {
            error.password.set(message);
        }
        return message == null;
    }

    public Boolean validatePassword() {
        initValidatePassword();
        return ValidationUtils.passwordValidationMessage(input.password.get(), true) == null;
    }

    public Boolean validatePasswordConfirm(Boolean showError) {
        Integer message = ValidationUtils.passwordConfirmValidationMessage(input.password.get(), input.passwordConfirm.get());
        if (message != null && !showError) {
            error.passwordConfirm.set(message);
        }
        return message == null;
    }

    public Boolean validatePasswordConfirm() {
        return ValidationUtils.passwordConfirmValidationMessage(input.password.get(), input.passwordConfirm.get()) == null;
    }

    private void initValidatePassword() {
        String passwordConfirm = input.passwordConfirm.get();
        if (passwordConfirm != null && !passwordConfirm.trim().isEmpty()) {
            validatePasswordConfirm();
        }
    }

    @Getter
    public class InputField {

        private final ObservableField<String> email = new ObservableField<String>() {

            @Override
            public void set(final String value) {
                super.set(value);
                notifyPropertyChanged(BR.registerFormValid);
            }
        };

        private final ObservableField<String> password = new ObservableField<String>() {

            @Override
            public void set(final String value) {
                super.set(value);
                notifyPropertyChanged(BR.registerFormValid);
            }
        };

        private final ObservableField<String> passwordConfirm = new ObservableField<String>() {

            @Override
            public void set(final String value) {
                super.set(value);
                notifyPropertyChanged(BR.registerFormValid);
            }
        };
    }

    @Getter
    public class ErrorField {

        private final ObservableField<Integer> email = new ObservableField<>();
        private final ObservableField<Integer> password = new ObservableField<>();
        private final ObservableField<Integer> passwordConfirm = new ObservableField<>();
    }
}
