package com.trujca.mapoli.data.categories.repository;

import com.trujca.mapoli.data.categories.model.Category;
import com.trujca.mapoli.data.util.RepositoryCallback;

import java.util.List;

public interface CategoriesRepository {

    void getAllCategories(RepositoryCallback<List<Category>, Void> callback);

    void addCategory(Category category, RepositoryCallback<Void, Void> callback);
}
