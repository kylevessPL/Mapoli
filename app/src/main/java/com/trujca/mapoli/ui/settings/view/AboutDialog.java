package com.trujca.mapoli.ui.settings.view;

import static java.util.Objects.requireNonNull;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.trujca.mapoli.R;
import com.trujca.mapoli.databinding.AboutDialogBinding;
import com.trujca.mapoli.databinding.DialogAddCategoryBinding;
import com.trujca.mapoli.ui.categories.model.Category;
import com.trujca.mapoli.ui.categories.view.AddCategoryDialog;
import com.trujca.mapoli.ui.categories.viewmodel.AddCategoryDialogViewModel;
import com.trujca.mapoli.ui.settings.viewmodel.AboutDialogViewModel;

import java.util.UUID;

public class AboutDialog extends DialogFragment {

    public static final String TAG = AboutDialog.class.getSimpleName();

    private AboutDialogViewModel viewModel;
    private AboutDialogBinding binding;

    @Override
    public void onStart() {
        super.onStart();
        binding.setViewModel(viewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AboutDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
