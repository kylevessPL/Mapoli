package com.trujca.mapoli.ui.places.viewmodel;

import androidx.lifecycle.ViewModel;

import com.hadilq.liveevent.LiveEvent;
import com.trujca.mapoli.data.places.model.PlaceCategory;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public class PlacesViewModel extends ViewModel {

    @Getter
    private final List<PlaceCategory> placeCategories = Arrays.asList(PlaceCategory.values());
    @Getter
    private final LiveEvent<Integer> navigateToPlacesCategoryFragment = new LiveEvent<>();

    public void navigateToPlacesCategoryFragment(PlaceCategory placeCategory) {
        navigateToPlacesCategoryFragment.setValue(placeCategory.getPlaceId());
    }
}
