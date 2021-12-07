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
import com.trujca.mapoli.ui.settings.viewmodel.ReportBugViewModel;

public class ReportBugDialog extends DialogFragment {

    public static final String TAG = ReportBugDialog.class.getSimpleName();

    private ReportBugViewModel viewModel;
    private ReportBugDialogBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ReportBugViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setViewModel(viewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ReportBugDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
