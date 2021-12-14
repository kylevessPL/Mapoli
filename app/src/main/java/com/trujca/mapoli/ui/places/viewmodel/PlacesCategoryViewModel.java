package com.trujca.mapoli.ui.places.viewmodel;

import static com.trujca.mapoli.util.Constants.LATITUDE_INITIAL;
import static com.trujca.mapoli.util.Constants.LONGITUDE_INITIAL;

import androidx.lifecycle.MutableLiveData;

import com.hadilq.liveevent.LiveEvent;
import com.trujca.mapoli.data.common.model.Coordinates;
import com.trujca.mapoli.data.places.model.PlaceNearby;
import com.trujca.mapoli.data.places.repository.PlacesRepository;
import com.trujca.mapoli.data.util.RepositoryCallback;
import com.trujca.mapoli.ui.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;

@HiltViewModel
public class PlacesCategoryViewModel extends BaseViewModel {

    private static final Integer PLACES_LIMIT = 15;
    private static final Integer PLACES_RADIUS = 5000;

    private final PlacesRepository placesRepository;

    @Getter
    private final MutableLiveData<List<PlaceNearby>> placesNearby = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Boolean> generalError = new LiveEvent<>();
    @Getter
    private final LiveEvent<String> navigateToMapFragment = new LiveEvent<>();

    @Inject
    public PlacesCategoryViewModel(PlacesRepository placesRepository) {
        this.placesRepository = placesRepository;
    }

    public void handleItemClicked(PlaceNearby place) {
        navigateToMapFragment.setValue(place.getPlaceId());
    }

    public void fetchPlacesInCategory(Integer categoryId) {
        executor.execute(() -> placesRepository.getPlacesNearby(
                new Coordinates((float) LATITUDE_INITIAL, (float) LONGITUDE_INITIAL), // use initial coords for now
                PLACES_RADIUS,
                categoryId,
                PLACES_LIMIT,
                new RepositoryCallback<List<PlaceNearby>, Void>() {

                    @Override
                    public void onLoading(final Boolean loading) {
                        isLoading.postValue(loading);
                    }

                    @Override
                    public void onSuccess(final List<PlaceNearby> model) {
                        placesNearby.postValue(model);
                    }

                    @Override
                    public void onError(final Void unused) {
                        generalError.postValue(true);
                    }
                }));
    }
}
