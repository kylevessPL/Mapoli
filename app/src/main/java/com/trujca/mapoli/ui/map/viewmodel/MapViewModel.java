package com.trujca.mapoli.ui.map.viewmodel;

import com.trujca.mapoli.data.favorites.model.Favorite;
import com.trujca.mapoli.data.favorites.repository.FavoritesRepository;
import com.trujca.mapoli.ui.base.BaseViewModel;
import java.util.List;

import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MapViewModel extends BaseViewModel {

    public List<Favorite> favorites;
    public FavoritesRepository repository;

    @Inject
    MapViewModel(FavoritesRepository repository) {
        this.repository = repository;
    }
}