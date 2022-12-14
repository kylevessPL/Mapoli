package com.trujca.mapoli.ui.base;

import static java.util.Objects.requireNonNull;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.trujca.mapoli.BR;

public abstract class BaseFragment<DB extends ViewDataBinding, VM extends ViewModel> extends Fragment {

    protected VM viewModel;
    protected DB binding;

    protected abstract Class<VM> getViewModelClass();

    @LayoutRes
    protected abstract int getLayoutRes();

    @StringRes
    protected abstract int getTitle();

    @Override
    @CallSuper
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(getViewModelClass());
        setup();
    }

    @Override
    @CallSuper
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(getTitle());
        setupView();
        return binding.getRoot();
    }

    @Override
    @CallSuper
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setVariable(BR.viewModel, viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        updateUI();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cleanup();
    }

    protected void setup() {
    }

    protected void setupView() {
    }

    protected void updateUI() {
    }

    protected void cleanup() {
    }
}
