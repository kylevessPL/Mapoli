package com.trujca.mapoli.ui.login.view;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.trujca.mapoli.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignInResultDialogFragment extends DialogFragment {

    public static final String TAG = SignInResultDialogFragment.class.getSimpleName();

    public static SignInResultDialogFragment newInstance(String message) {
        SignInResultDialogFragment fragment = new SignInResultDialogFragment();
        Bundle args = new Bundle();
        args.putString("message", message);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        String message = requireArguments().getString("message");
        return new MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.signin))
                .setMessage(message)
                .setPositiveButton(getString(android.R.string.ok), null)
                .create();
    }
}
