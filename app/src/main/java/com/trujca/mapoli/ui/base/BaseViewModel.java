package com.trujca.mapoli.ui.base;

import androidx.lifecycle.ViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BaseViewModel extends ViewModel {

    protected final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCleared() {
        super.onCleared();
        executor.shutdown();
        cleanup();
    }

    protected void cleanup() {
    }
}
