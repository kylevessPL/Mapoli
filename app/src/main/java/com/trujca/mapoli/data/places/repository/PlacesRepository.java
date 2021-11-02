package com.trujca.mapoli.data.places.repository;

import com.trujca.mapoli.data.places.model.PlaceCategoryResponse;

import java.util.List;

public interface PlacesRepository {

    List<PlaceCategoryResponse> getAllPlaceCategories();
}
