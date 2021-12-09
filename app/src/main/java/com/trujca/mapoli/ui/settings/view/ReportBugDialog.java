package com.trujca.mapoli.ui.settings.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.trujca.mapoli.databinding.ReportBugDialogBinding;
import com.trujca.mapoli.ui.settings.viewmodel.SettingsViewModel;

public class ReportBugDialog extends DialogFragment {

    public static final String TAG = ReportBugDialog.class.getSimpleName();

    private ReportBugDialogBinding binding;
    private SettingsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ReportBugDialogBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setViewModel(viewModel);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.closeReportBugDialog.observe(getViewLifecycleOwner(), value -> dismiss());
    }
}
