package com.trujca.mapoli.ui.map.viewmodel;

import static java.util.Objects.requireNonNull;

import android.view.MenuItem;

import androidx.core.util.Pair;
import androidx.lifecycle.MutableLiveData;

import com.hadilq.liveevent.LiveEvent;
import com.trujca.mapoli.data.favorites.model.Favorite;
import com.trujca.mapoli.data.favorites.repository.FavoritesRepository;
import com.trujca.mapoli.data.places.model.Place;
import com.trujca.mapoli.data.places.repository.PlacesRepository;
import com.trujca.mapoli.data.util.RepositoryCallback;
import com.trujca.mapoli.ui.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;

@HiltViewModel
public class MapViewModel extends BaseViewModel {

    private final FavoritesRepository favoritesRepository;
    private final PlacesRepository placesRepository;

    @Getter
    private final MutableLiveData<List<Favorite>> favorites = new MutableLiveData<>(new ArrayList<>());
    @Getter
    private final MutableLiveData<Place> place = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Boolean> generalError = new LiveEvent<>();
    @Getter
    private final LiveEvent<Boolean> navigateToCurrentLocation = new LiveEvent<>();
    @Getter
    private final List<Pair<MenuItem, Favorite>> favoritesMenuItems = new ArrayList<>();

    @Inject
    public MapViewModel(FavoritesRepository favoritesRepository, PlacesRepository placesRepository) {
        this.favoritesRepository = favoritesRepository;
        this.placesRepository = placesRepository;
    }

    public void fetchFavorites() {
        executor.execute(() -> favoritesRepository.getAllFavorites(new RepositoryCallback<List<Favorite>, Void>() {

            @Override
            public void onLoading(final Boolean loading) {
                isLoading.postValue(loading);
            }

            @Override
            public void onSuccess(final List<Favorite> model) {
                favorites.postValue(model);
            }

            @Override
            public void onError(final Void unused) {
                generalError.postValue(true);
            }
        }));
    }

    public void addFavorite(Favorite favorite) {
        executor.execute(() -> favoritesRepository.addFavorite(favorite, new RepositoryCallback<Void, Void>() {

            @Override
            public void onLoading(final Boolean loading) {
                isLoading.postValue(loading);
            }

            @Override
            public void onSuccess(final Void unused) {
                List<Favorite> favorites = requireNonNull(MapViewModel.this.favorites.getValue());
                favorites.add(favorite);
                MapViewModel.this.favorites.postValue(new ArrayList<>(favorites));
            }

            @Override
            public void onError(final Void unused) {
                generalError.postValue(true);
            }
        }));
    }

    public void deleteFavorite(Favorite favorite) {
        executor.execute(() -> favoritesRepository.deleteFavorite(favorite, new RepositoryCallback<Void, Void>() {

            @Override
            public void onLoading(final Boolean loading) {
                isLoading.postValue(loading);
            }

            @Override
            public void onSuccess(final Void unused) {
                List<Favorite> favorites = requireNonNull(MapViewModel.this.favorites.getValue());
                favorites.removeIf(element -> element.getDocumentId().equals(favorite.getDocumentId()));
                MapViewModel.this.favorites.postValue(new ArrayList<>(favorites));
            }

            @Override
            public void onError(final Void unused) {
                generalError.postValue(true);
            }
        }));
    }

    public void fetchPlaceDetails(String placeId) {
        executor.execute(() -> placesRepository.getPlaceDetails(
                placeId,
                new RepositoryCallback<Place, Void>() {

                    @Override
                    public void onLoading(final Boolean loading) {
                        isLoading.postValue(loading);
                    }

                    @Override
                    public void onSuccess(final Place model) {
                        place.postValue(model);
                    }

                    @Override
                    public void onError(final Void unused) {
                        generalError.postValue(true);
                    }
                }));
    }

    public void navigateToCurrentLocation() {
        navigateToCurrentLocation.setValue(true);
    }
}