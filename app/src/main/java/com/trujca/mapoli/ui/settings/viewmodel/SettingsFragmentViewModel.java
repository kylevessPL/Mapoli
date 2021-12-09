package com.trujca.mapoli.ui.settings.viewmodel;

import com.trujca.mapoli.ui.base.BaseViewModel;
import com.trujca.mapoli.ui.common.CurrentUserLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SettingsFragmentViewModel extends BaseViewModel {

    public CurrentUserLiveData currentUserLiveData = new CurrentUserLiveData();

    @Inject
    public SettingsFragmentViewModel(){
    }
}
