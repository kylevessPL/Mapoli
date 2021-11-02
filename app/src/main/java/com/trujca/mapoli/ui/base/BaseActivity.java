package com.trujca.mapoli.ui.base;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseActivity<DB extends ViewDataBinding> extends AppCompatActivity {

    protected DB binding;

    @LayoutRes
    protected abstract int getLayoutRes();

    @Override
    @CallSuper
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutRes());
        setupView();
        updateUI();
    }

    protected void updateUI() {
    }

    protected void setupView() {
    }
}
