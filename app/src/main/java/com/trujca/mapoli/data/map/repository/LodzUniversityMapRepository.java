package com.trujca.mapoli.data.map.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.trujca.mapoli.data.map.api.LodzUniversityBuildingsService;
import com.trujca.mapoli.data.map.model.LodzUniversityBuilding;
import com.trujca.mapoli.data.util.RepositoryCallback;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LodzUniversityMapRepository implements MapRepository {

    private static final String TAG = LodzUniversityMapRepository.class.getSimpleName();

    private final LodzUniversityBuildingsService service;

    @Inject
    public LodzUniversityMapRepository(LodzUniversityBuildingsService service) {
        this.service = service;
    }

    @Override
    public void getBuildings(final RepositoryCallback<List<LodzUniversityBuilding>, Void> callback) {
        callback.onLoading(true);
        service.getBuildings().enqueue(new Callback<List<LodzUniversityBuilding>>() {

            @Override
            public void onResponse(@NonNull final Call<List<LodzUniversityBuilding>> call, @NonNull final Response<List<LodzUniversityBuilding>> response) {
                callback.onLoading(false);
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                    Log.w(TAG, "getBuildings:success");
                } else {
                    callback.onError(null);
                    Log.w(TAG, "getBuildings:failure");
                }
            }

            @Override
            public void onFailure(@NonNull final Call<List<LodzUniversityBuilding>> call, @NonNull final Throwable ex) {
                callback.onLoading(false);
                callback.onError(null);
                Log.w(TAG, "getBuildings:failure", ex);
            }
        });
    }
}
