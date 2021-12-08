package com.trujca.mapoli.ui.places.viewmodel;

import static com.trujca.mapoli.util.Constants.LATITUDE_INITIAL;
import static com.trujca.mapoli.util.Constants.LONGTITUDE_INITIAL;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.trujca.mapoli.data.common.model.Coordinates;
import com.trujca.mapoli.data.places.model.PlaceNearby;
import com.trujca.mapoli.data.places.repository.PlacesRepository;
import com.trujca.mapoli.data.util.RepositoryCallback;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;

@HiltViewModel
public class PlacesCategoryViewModel extends ViewModel {

    private static final Integer PLACES_LIMIT = 10;
    private static final Integer PLACES_RADIUS = 5000;

    private final PlacesRepository placesRepository;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Getter
    private final MutableLiveData<List<PlaceNearby>> placesNearby = new MutableLiveData<>();

    @Inject
    public PlacesCategoryViewModel(PlacesRepository placesRepository) {
        this.placesRepository = placesRepository;
    }

    public void showPlaceOnMap(PlaceNearby place) {
    }

    public void fetchPlacesInCategory(Integer categoryId) {
        executor.execute(() -> placesRepository.getPlacesNearby(
                new Coordinates((float) LATITUDE_INITIAL, (float) LONGTITUDE_INITIAL), // use initial coords for now
                PLACES_RADIUS,
                categoryId,
                PLACES_LIMIT,
                new RepositoryCallback<List<PlaceNearby>>() {

                    @Override
                    public void onSuccess(final List<PlaceNearby> model) {
                        placesNearby.postValue(model);
                    }

                    @Override
                    public void onError(final String msg) {
                        // we'll think 'bout later, ookaay?
                    }
                }));
    }
}
