package com.trujca.mapoli.data.places.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.trujca.mapoli.data.common.model.Coordinates;
import com.trujca.mapoli.data.places.api.FoursquarePlacesService;
import com.trujca.mapoli.data.places.model.Place;
import com.trujca.mapoli.data.places.model.PlaceNearby;
import com.trujca.mapoli.data.util.RepositoryCallback;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoursquarePlacesRepository implements PlacesRepository {

    private static final String TAG = FoursquarePlacesRepository.class.getSimpleName();

    private final FoursquarePlacesService service;

    @Inject
    public FoursquarePlacesRepository(FoursquarePlacesService service) {
        this.service = service;
    }

    @Override
    public void getPlaceDetails(Integer placeId, RepositoryCallback<Place> callback) {
        service.getPlaceDetails(placeId).enqueue(new Callback<Place>() {

            @Override
            public void onResponse(@NonNull final Call<Place> call, @NonNull final Response<Place> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                    Log.w(TAG, "getPlaceDetails:success");
                } else {
                    callback.onError(null);
                    Log.w(TAG, "getPlaceDetails:failure");
                }
            }

            @Override
            public void onFailure(@NonNull final Call<Place> call, @NonNull final Throwable ex) {
                callback.onError(ex.getMessage());
                Log.w(TAG, "getPlaceDetails:failure", ex);
            }
        });
    }

    @Override
    public void getPlacesNearby(
            Coordinates coordinates,
            Integer radius,
            Integer categoryId,
            Integer limit,
            RepositoryCallback<List<PlaceNearby>> callback
    ) {
        service.getPlacesNearby(coordinates, radius, categoryId, limit).enqueue(new Callback<List<PlaceNearby>>() {

            @Override
            public void onResponse(@NonNull final Call<List<PlaceNearby>> call, @NonNull final Response<List<PlaceNearby>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                    Log.w(TAG, "getPlacesNearby:success");
                } else {
                    callback.onError(null);
                    Log.w(TAG, "getPlacesNearby:failure");
                }
            }

            @Override
            public void onFailure(@NonNull final Call<List<PlaceNearby>> call, @NonNull final Throwable ex) {
                callback.onError(ex.getMessage());
                Log.w(TAG, "getPlacesNearby:failure", ex);
            }
        });
    }
}
