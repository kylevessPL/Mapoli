package com.trujca.mapoli.data.map.repository;

import com.trujca.mapoli.data.map.model.LodzUniversityBuilding;
import com.trujca.mapoli.data.util.RepositoryCallback;

import java.util.List;

public interface MapRepository {
    void getBuildings(RepositoryCallback<List<LodzUniversityBuilding>, Void> callback);
}
