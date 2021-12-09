package com.trujca.mapoli.ui.settings.view;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;

import com.trujca.mapoli.R;
import com.trujca.mapoli.databinding.ActivitySettingsBinding;
import com.trujca.mapoli.ui.base.BaseActivity;
import com.trujca.mapoli.ui.settings.viewmodel.SettingsViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SettingsActivity extends BaseActivity<ActivitySettingsBinding, SettingsViewModel> {

    @Override
    protected Class<SettingsViewModel> getViewModelClass() {
        return SettingsViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_settings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}