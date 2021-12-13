package com.trujca.mapoli.ui.categories.viewmodel;

import static java.util.Objects.requireNonNull;

import androidx.lifecycle.MutableLiveData;

import com.hadilq.liveevent.LiveEvent;
import com.trujca.mapoli.data.categories.model.Category;
import com.trujca.mapoli.data.categories.repository.CategoriesRepository;
import com.trujca.mapoli.data.util.RepositoryCallback;
import com.trujca.mapoli.ui.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;

@HiltViewModel
public class CategoriesViewModel extends BaseViewModel {

    private final CategoriesRepository categoriesRepository;
    @Getter
    private final MutableLiveData<List<Category>> categories = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Boolean> generalError = new LiveEvent<>();

    @Inject
    public CategoriesViewModel(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
        fetchAllCategories();
    }

    public void doOnPlaceCategoryClicked(final Category category) {
    }

    public void fetchAllCategories() {
        executor.execute(() -> categoriesRepository.getAllCategories(new RepositoryCallback<List<Category>, Void>() {

            @Override
            public void onLoading(final Boolean loading) {
                isLoading.postValue(loading);
            }

            @Override
            public void onSuccess(final List<Category> model) {
                categories.postValue(model);
            }

            @Override
            public void onError(final Void unused) {
                generalError.postValue(true);
            }
        }));
    }

    public void addNewCategory(Category category) {
        executor.execute(() -> categoriesRepository.addCategory(category, new RepositoryCallback<Void, Void>() {

            @Override
            public void onLoading(final Boolean loading) {
                isLoading.postValue(loading);
            }

            @Override
            public void onSuccess(final Void model) {
                List<Category> categories = requireNonNull(CategoriesViewModel.this.categories.getValue());
                categories.add(category);
                CategoriesViewModel.this.categories.postValue(new ArrayList<>(categories));
            }

            @Override
            public void onError(final Void unused) {
                generalError.postValue(true);
            }
        }));
    }
}
