package com.trujca.mapoli.ui.settings.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AboutDialogViewModel extends ViewModel {

    @Inject
    public AboutDialogViewModel() {
    }

    public LiveData<String> liveData = new MutableLiveData<>();
}
