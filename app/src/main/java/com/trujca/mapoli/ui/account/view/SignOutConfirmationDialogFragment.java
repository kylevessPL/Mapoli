package com.trujca.mapoli.ui.account.view;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.trujca.mapoli.R;
import com.trujca.mapoli.ui.account.viewmodel.AccountViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignOutConfirmationDialogFragment extends DialogFragment {

    public static final String TAG = SignOutConfirmationDialogFragment.class.getSimpleName();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        AccountViewModel accountViewModel = new ViewModelProvider(requireParentFragment()).get(AccountViewModel.class);
        return new MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.signout))
                .setMessage(getString(R.string.sign_out_confirmation_dialog_message))
                .setPositiveButton(getString(R.string.signout), (dialog, which) -> accountViewModel.signOut())
                .setNegativeButton(getString(android.R.string.cancel), null)
                .create();
    }
}
