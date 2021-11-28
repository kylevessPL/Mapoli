package com.trujca.mapoli.ui.base;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public abstract class BaseActivity<DB extends ViewDataBinding, VM extends ViewModel> extends AppCompatActivity {

    protected VM viewModel;
    protected DB binding;

    protected abstract Class<VM> getViewModelClass();

    @LayoutRes
    protected abstract int getLayoutRes();

    @Override
    @CallSuper
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutRes());
        viewModel = new ViewModelProvider(this).get(getViewModelClass());
        setup();
        updateUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanup();
    }

    protected void setup() {
    }

    protected void updateUI() {
    }

    protected void cleanup() {
    }
}
