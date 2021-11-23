package com.trujca.mapoli.ui.categories.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.trujca.mapoli.data.categories.repository.CategoriesRepository;
import com.trujca.mapoli.data.util.RepositoryCallback;
import com.trujca.mapoli.ui.categories.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;

@HiltViewModel
public class CategoriesViewModel extends ViewModel {

    private final CategoriesRepository categoriesRepository;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
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
        executor.execute(() -> categoriesRepository.getAllCategories(new RepositoryCallback<List<Category>>() {

            @Override
            public void onSuccess(final List<Category> model) {
                categoriesData.postValue(model);
            }

            @Override
            public void onError(final Throwable ex) {
                // do sth with error, ex. show dialog with error msg
            }
        }));
    }

    public void addNewCategory(Category category) {
        executor.execute(() -> categoriesRepository.addCategory(category, new RepositoryCallback<Void>() {

            @Override
            public void onSuccess(final Void model) {
                // List<Category> categories = requireNonNull(categoriesData.getValue()); TODO jak juz bedzie dzialalo dodawanie wielu kategorii do firestore
                List<Category> categories = new ArrayList<>(); // for now, change later
                categories.add(category);
                categoriesData.postValue(new ArrayList<>(categories));
            }

            @Override
            public void onError(final Throwable ex) {
                // do sth with error, ex. show dialog with error msg
            }
        }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executor.shutdown();
    }
}
