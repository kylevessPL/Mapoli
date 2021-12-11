package com.trujca.mapoli.ui.register.view;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.trujca.mapoli.R;
import com.trujca.mapoli.ui.register.viewmodel.RegisterViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignUpResultDialogFragment extends DialogFragment {

    public static final String TAG = SignUpResultDialogFragment.class.getSimpleName();

    public static SignUpResultDialogFragment newInstance(String message, boolean success) {
        SignUpResultDialogFragment fragment = new SignUpResultDialogFragment();
        Bundle args = new Bundle();
        args.putString("message", message);
        args.putBoolean("success", success);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        RegisterViewModel accountViewModel = new ViewModelProvider(requireParentFragment()).get(RegisterViewModel.class);
        String message = requireArguments().getString("message");
        boolean success = requireArguments().getBoolean("success");
        return new MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.signup))
                .setMessage(message)
                .setPositiveButton(
                        success ? getString(R.string.signin) : getString(android.R.string.ok),
                        success ? (dialog, which) -> accountViewModel.signInAction() : null)
                .create();
    }
}
