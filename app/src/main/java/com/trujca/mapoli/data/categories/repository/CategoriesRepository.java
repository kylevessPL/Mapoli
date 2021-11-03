package com.trujca.mapoli.data.categories.repository;

import com.trujca.mapoli.data.util.RepositoryCallback;
import com.trujca.mapoli.ui.categories.model.Category;

import java.util.List;

public interface CategoriesRepository {

    void getAllCategories(RepositoryCallback<List<Category>> callback);

    void addCategory(Category category, RepositoryCallback<Void> callback);
}
