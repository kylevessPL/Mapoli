package com.trujca.mapoli.ui.map.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.hadilq.liveevent.LiveEvent;
import com.trujca.mapoli.data.places.model.Place;
import com.trujca.mapoli.data.places.repository.PlacesRepository;
import com.trujca.mapoli.data.util.RepositoryCallback;
import com.trujca.mapoli.ui.base.BaseViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;

@HiltViewModel
public class MapViewModel extends BaseViewModel {

    private final PlacesRepository placesRepository;

    @Getter
    private final MutableLiveData<Place> place = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Boolean> generalError = new LiveEvent<>();
    @Getter
    private final LiveEvent<Boolean> navigateToCurrentLocation = new LiveEvent<>();

    @Inject
    public MapViewModel(PlacesRepository placesRepository) {
        this.placesRepository = placesRepository;
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