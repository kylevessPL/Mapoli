package com.trujca.mapoli.ui.categories.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddCategoryDialogViewModel extends ViewModel {

    public ObservableField<String> categoryNameInput = new ObservableField<>("");

    @Inject
    public AddCategoryDialogViewModel() {
    }
}
