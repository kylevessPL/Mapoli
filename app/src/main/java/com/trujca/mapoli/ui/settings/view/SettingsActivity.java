package com.trujca.mapoli.ui.settings.view;


import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModel;
import com.trujca.mapoli.R;
import com.trujca.mapoli.ui.base.BaseActivity;

import dagger.hilt.android.AndroidEntryPoint;
import com.trujca.mapoli.databinding.ActivitySettingsBinding;

@AndroidEntryPoint
public class SettingsActivity extends BaseActivity<ActivitySettingsBinding, ViewModel>{

    @Override
    protected Class getViewModelClass() {
        return null;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_settings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_settings);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}