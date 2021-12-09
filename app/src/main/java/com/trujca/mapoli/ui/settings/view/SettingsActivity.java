package com.trujca.mapoli.ui.settings.view;


import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.trujca.mapoli.R;
import com.trujca.mapoli.ui.base.BaseActivity;

import dagger.hilt.android.AndroidEntryPoint;
import com.trujca.mapoli.databinding.ActivitySettingsBinding;
import com.trujca.mapoli.ui.settings.viewmodel.ReportBugViewModel;

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
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        ReportBugViewModel reportBugViewModel = new ViewModelProvider(this).get(ReportBugViewModel.class);
        reportBugViewModel.toastMessage.observe(this, message -> {
            if (!message.equals("")){
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });
        return super.onCreateView(name, context, attrs);
    }
}