package com.trujca.mapoli.ui.categories.view;

import static java.util.Objects.requireNonNull;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.trujca.mapoli.R;
import com.trujca.mapoli.data.categories.model.Category;
import com.trujca.mapoli.databinding.DialogAddCategoryBinding;
import com.trujca.mapoli.ui.categories.viewmodel.AddCategoryDialogViewModel;
import com.trujca.mapoli.ui.categories.viewmodel.CategoriesViewModel;

import java.util.UUID;

public class AddCategoryDialogFragment extends DialogFragment {

    public static final String TAG = AddCategoryDialogFragment.class.getSimpleName();

    private DialogAddCategoryBinding binding;
    private AddCategoryDialogViewModel viewModel;
    private CategoriesViewModel parentFragmentViewModel;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddCategoryDialogViewModel.class);
        parentFragmentViewModel = new ViewModelProvider(requireParentFragment()).get(CategoriesViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        binding = DialogAddCategoryBinding.inflate(getLayoutInflater());
        return new MaterialAlertDialogBuilder(requireContext())
                .setView(binding.getRoot())
                .setTitle(R.string.add_category)
                .setPositiveButton(R.string.add, (dialog, i) -> {
                    Category category = new Category(UUID.randomUUID().toString(), requireNonNull(binding.addCategoryDialogInput.getEditText()).getText().toString());
                    parentFragmentViewModel.addNewCategory(category);
                })
                .setNegativeButton(android.R.string.cancel, (dialogInterface, i) -> {
                })
                .create();
    }
}

