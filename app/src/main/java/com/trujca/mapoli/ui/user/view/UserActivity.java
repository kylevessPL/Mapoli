package com.trujca.mapoli.ui.user.view;

import com.trujca.mapoli.R;
import com.trujca.mapoli.databinding.ActivityUserBinding;
import com.trujca.mapoli.ui.base.BaseActivity;
import com.trujca.mapoli.ui.user.viewmodel.UserViewModel;

public class UserActivity extends BaseActivity<ActivityUserBinding, UserViewModel> {

    @Override
    public Class<UserViewModel> getViewModelClass() {
        return UserViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_user;
    }
}
