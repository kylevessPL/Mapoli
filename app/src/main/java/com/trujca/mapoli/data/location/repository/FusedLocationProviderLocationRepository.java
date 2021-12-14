package com.trujca.mapoli.data.location.repository;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.trujca.mapoli.data.common.model.Coordinates;
import com.trujca.mapoli.data.util.RepositoryCallback;

import javax.inject.Inject;

@SuppressWarnings("MissingPermission")
public class FusedLocationProviderLocationRepository implements LocationRepository {

    private static final String TAG = FusedLocationProviderLocationRepository.class.getSimpleName();

    private final FusedLocationProviderClient locationProviderClient;

    @Inject
    public FusedLocationProviderLocationRepository(FusedLocationProviderClient locationProviderClient) {
        this.locationProviderClient = locationProviderClient;
    }

    @Override
    public void getCurrentLocation(RepositoryCallback<Coordinates, Void> callback) {
        callback.onLoading(true);
        locationProviderClient.getLastLocation().addOnCompleteListener(task -> {
            callback.onLoading(false);
            try {
                Location location = task.getResult(ApiException.class);
                callback.onSuccess(locationToCoordinates(location));
                Log.w(TAG, "getCurrentLocation:success");
            } catch (Exception ex) {
                callback.onError(null);
                Log.w(TAG, "getCurrentLocation:failure");
            }
        });
    }

    private Coordinates locationToCoordinates(Location location) {
        return new Coordinates((float) location.getLatitude(), (float) location.getLongitude());
    }
}
