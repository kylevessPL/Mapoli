package com.trujca.mapoli.data.location.repository;

import com.trujca.mapoli.data.common.model.Coordinates;
import com.trujca.mapoli.data.util.RepositoryCallback;

public interface LocationRepository {
    void getCurrentLocation(RepositoryCallback<Coordinates, Void> callback);
}
