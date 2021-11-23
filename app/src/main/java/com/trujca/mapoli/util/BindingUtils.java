package com.trujca.mapoli.util;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static lombok.AccessLevel.PRIVATE;

import android.net.Uri;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import agency.tango.android.avatarview.ImageLoaderBase;
import agency.tango.android.avatarview.views.AvatarView;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class BindingUtils {

    @BindingAdapter("android:errorText")
    public static void errorText(TextInputLayout input, @Nullable @StringRes Integer errorText) {
        if (errorText != null) {
            input.setError(input.getContext().getString(errorText));
        }
    }

    @BindingAdapter("android:loseFocusOnDone")
    public static void loseFocusOnDone(TextInputEditText input, Boolean value) {
        if (!value) {
            return;
        }
        input.setOnEditorActionListener((view, actionId, event) -> {
            if (actionId != IME_ACTION_DONE) {
                return false;
            }
            view.clearFocus();
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return true;
        });
    }

    @BindingAdapter({"android:userAvatar", "android:userName"})
    public static void userAvatarData(AvatarView avatarView, Uri userAvatar, String userName) {
        ImageLoaderBase imageLoader = new GlideLoader();
        imageLoader.loadImage(avatarView, userAvatar.toString(), userName);
    }

    @BindingConversion
    public static int convertBooleanToVisibility(boolean visible) {
        if (visible) {
            return VISIBLE;
        }
        return GONE;
    }
}
