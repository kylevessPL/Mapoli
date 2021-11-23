package com.trujca.mapoli.ui.user.viewmodel;

import androidx.lifecycle.LiveData;

import com.trujca.mapoli.ui.base.BaseViewModel;
import com.trujca.mapoli.ui.common.CurrentUserLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;

@HiltViewModel
public class UserViewModel extends BaseViewModel {

    @Getter
    private final LiveData<Boolean> currentUser = new CurrentUserLiveData();

    @Inject
    public UserViewModel() {
    }
}
