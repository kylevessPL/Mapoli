package com.trujca.mapoli.ui.places.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.trujca.mapoli.R;
import com.trujca.mapoli.data.places.repository.PlacesRepository;
import com.trujca.mapoli.ui.base.BaseViewModel;
import com.hadilq.liveevent.LiveEvent;
import com.trujca.mapoli.data.places.model.PlaceCategory;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@HiltViewModel
public class PlacesViewModel extends BaseViewModel {

    @Getter
    private final List<PlaceCategory> placeCategories = Arrays.asList(PlaceCategory.values());
    @Getter
    private final LiveEvent<Integer> navigateToPlacesCategoryFragment = new LiveEvent<>();

    public void navigateToPlacesCategoryFragment(PlaceCategory placeCategory) {
        navigateToPlacesCategoryFragment.setValue(placeCategory.getPlaceId());
    }
}
