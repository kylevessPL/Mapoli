package com.trujca.mapoli.data.favorites.repository;

import com.trujca.mapoli.data.favorites.model.Favorite;
import com.trujca.mapoli.data.util.RepositoryCallback;

import java.util.List;

public interface FavoritesRepository {

    void getAllFavorites(RepositoryCallback<List<Favorite>, Void> callback);

    void addFavorite(Favorite favorite, RepositoryCallback<Void, Void> callback);

    void deleteFavorite(String documentId, RepositoryCallback<Void, Void> callback);
}
