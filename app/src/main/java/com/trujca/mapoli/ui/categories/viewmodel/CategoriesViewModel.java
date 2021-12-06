package com.trujca.mapoli.ui.categories.viewmodel;

import static java.util.Objects.requireNonNull;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.trujca.mapoli.data.categories.repository.CategoriesRepository;
import com.trujca.mapoli.data.util.RepositoryCallback;
import com.trujca.mapoli.ui.base.BaseViewModel;
import com.trujca.mapoli.ui.categories.model.Category;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;

@HiltViewModel
public class CategoriesViewModel extends BaseViewModel {

    private final CategoriesRepository categoriesRepository;
    @Getter
    private final MutableLiveData<List<Category>> categoriesData = new MutableLiveData<>(new ArrayList<>());
    public ObservableField<String> categoryNameInput = new ObservableField<>("");

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
            public void onLoading(final Boolean isLoading) {
                // update loading livedata and display progressindicator with proper visibility
            }

            @Override
            public void onSuccess(final List<Category> model) {
                categoriesData.postValue(model);
            }

            @Override
            public void onError(final Void ex) {
                // do sth with error, ex. show dialog with error msg
            }
        }));
    }

    public void addNewCategory(Category category) {
        executor.execute(() -> categoriesRepository.addCategory(category, new RepositoryCallback<Void, Void>() {

            @Override
            public void onLoading(final Boolean isLoading) {
                // update loading livedata and display progressindicator with proper visibility
            }

            @Override
            public void onSuccess(final Void model) {
                List<Category> categories = requireNonNull(categoriesData.getValue());
                categories.add(category);
                categoriesData.postValue(new ArrayList<>(categories));
            }

            @Override
            public void onError(final Void ex) {
                // do sth with error, ex. show dialog with error msg
            }
        }));
    }
}
