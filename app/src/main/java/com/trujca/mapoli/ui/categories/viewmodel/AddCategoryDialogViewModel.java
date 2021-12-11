package com.trujca.mapoli.ui.categories.viewmodel;

import androidx.databinding.ObservableField;

import com.trujca.mapoli.ui.base.BaseViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddCategoryDialogViewModel extends BaseViewModel {

    public ObservableField<String> categoryNameInput = new ObservableField<>("");

    @Inject
    public AddCategoryDialogViewModel() {
    }
}
