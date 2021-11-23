package com.trujca.mapoli.util;

import static lombok.AccessLevel.PRIVATE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.trujca.mapoli.R;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class ValidationUtils {

    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";

    public static Integer emailValidationMessage(@Nullable String email) {
        if (email == null || email.trim().isEmpty()) {
            return R.string.empty_not_allowed;
        }
        if (!email.matches(EMAIL_REGEX)) {
            return R.string.email_not_valid;
        }
        return null;
    }

    public static Integer passwordValidationMessage(@Nullable String password, @NonNull Boolean confirmation) {
        if (password == null || password.trim().isEmpty()) {
            return R.string.empty_not_allowed;
        }
        if (password.length() < 6 && confirmation) {
            return R.string.min_six_chars_allowed;
        }
        return null;
    }

    public static Integer passwordConfirmValidationMessage(@Nullable String password, @Nullable String passwordConfirm) {
        if (passwordConfirm == null || passwordConfirm.trim().isEmpty()) {
            return R.string.empty_not_allowed;
        }
        if (!(password == null || password.trim().isEmpty()) && !password.equals(passwordConfirm)) {
            return R.string.passwords_not_match;
        }
        return null;
    }
}
