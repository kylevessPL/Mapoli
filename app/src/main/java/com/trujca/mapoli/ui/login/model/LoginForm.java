package com.trujca.mapoli.ui.login.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.trujca.mapoli.BR;
import com.trujca.mapoli.util.ValidationUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
public class LoginForm extends BaseObservable {

    private final InputField input = new InputField();
    private final ErrorField error = new ErrorField();

    private Boolean processing = false;

    public void setIsProcessing(Boolean processing) {
        this.processing = processing;
        notifyPropertyChanged(BR.loginFormValid);
    }

    @Bindable
    public boolean isLoginFormValid() {
        return !processing && validateEmail(false) && validatePassword(false);
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
        Integer message = ValidationUtils.passwordValidationMessage(input.password.get(), false);
        if (message != null && !showError) {
            error.password.set(message);
        }
        return message == null;
    }

    public Boolean validatePassword() {
        return ValidationUtils.passwordValidationMessage(input.password.get(), false) == null;
    }

    @Getter
    public class InputField {

        private final ObservableField<String> email = new ObservableField<String>() {

            @Override
            public void set(final String value) {
                super.set(value);
                notifyPropertyChanged(BR.loginFormValid);
            }
        };

        private final ObservableField<String> password = new ObservableField<String>() {

            @Override
            public void set(final String value) {
                super.set(value);
                notifyPropertyChanged(BR.loginFormValid);
            }
        };
    }

    @Getter
    @Setter
    public class ErrorField {

        private final ObservableField<Integer> email = new ObservableField<>();
        private final ObservableField<Integer> password = new ObservableField<>();
    }
}
