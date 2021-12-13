package com.trujca.mapoli.data.places.repository;

import com.trujca.mapoli.data.common.model.Coordinates;
import com.trujca.mapoli.data.places.model.Place;
import com.trujca.mapoli.data.places.model.PlaceNearby;
import com.trujca.mapoli.data.util.RepositoryCallback;

import java.util.List;

public interface PlacesRepository {

    void getPlaceDetails(String placeId, RepositoryCallback<Place, Void> callback);

    void getPlacesNearby(
            Coordinates coordinates,
            Integer radius,
            Integer categoryId,
            Integer limit,
            RepositoryCallback<List<PlaceNearby>, Void> callback
    );
}
