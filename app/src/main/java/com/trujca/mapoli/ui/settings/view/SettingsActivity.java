package com.trujca.mapoli.ui.settings.view;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModel;

import com.trujca.mapoli.R;
import com.trujca.mapoli.databinding.ActivitySettingsBinding;
import com.trujca.mapoli.ui.base.BaseActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SettingsActivity extends BaseActivity<ActivitySettingsBinding, ViewModel> {

    @Override
    protected Class<ViewModel> getViewModelClass() {
        return null;
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

    @Override
    protected void setup() {
        setSupportActionBar(binding.toolbar);
    }
}