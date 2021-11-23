package com.trujca.mapoli.data.places.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.trujca.mapoli.data.places.model.PlaceCategoryResponse;

import java.util.List;

import javax.inject.Inject;

public class FoursquarePlacesRepository implements PlacesRepository {

    private static final String TAG = FoursquarePlacesRepository.class.getSimpleName();

    private final FirebaseFirestore firestore;

    @Inject
    public FoursquarePlacesRepository(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public List<PlaceCategoryResponse> getAllPlaceCategories() {


        throw new UnsupportedOperationException(); // TODO implement
    }
}
