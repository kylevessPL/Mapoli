package com.trujca.mapoli.ui.places.viewmodel;

import androidx.core.util.Pair;

import com.hadilq.liveevent.LiveEvent;
import com.trujca.mapoli.data.places.model.PlaceCategory;
import com.trujca.mapoli.ui.base.BaseViewModel;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public class PlacesViewModel extends BaseViewModel {

    @Getter
    private final List<PlaceCategory> placeCategories = Arrays.asList(PlaceCategory.values());
    @Getter
    private final LiveEvent<Pair<Integer, Integer>> navigateToPlacesCategoryFragment = new LiveEvent<>();

    public void navigateToPlacesCategoryFragment(PlaceCategory placeCategory) {
        navigateToPlacesCategoryFragment.setValue(new Pair<>(placeCategory.getPlaceId(), placeCategory.getNameId()));
    }
}
