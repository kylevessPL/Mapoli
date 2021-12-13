package com.trujca.mapoli.ui.settings.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.trujca.mapoli.databinding.AboutDialogBinding;

public class AboutDialog extends DialogFragment {

    public static final String TAG = AboutDialog.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AboutDialogBinding binding = AboutDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
