package com.trujca.mapoli.ui.settings.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.trujca.mapoli.databinding.AboutDialogBinding;
import com.trujca.mapoli.ui.settings.viewmodel.AboutDialogViewModel;

public class AboutDialog extends DialogFragment {

    public static final String TAG = AboutDialog.class.getSimpleName();

    private AboutDialogViewModel viewModel;
    private AboutDialogBinding binding;

    @Override
    public void onStart() {
        super.onStart();
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AboutDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
