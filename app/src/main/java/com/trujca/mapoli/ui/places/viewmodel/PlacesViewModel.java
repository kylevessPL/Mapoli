package com.trujca.mapoli.ui.places.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.trujca.mapoli.R;
import com.trujca.mapoli.data.places.repository.PlacesRepository;
import com.trujca.mapoli.ui.places.model.PlaceCategory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;

@HiltViewModel
public class PlacesViewModel extends ViewModel {

    private final PlacesRepository placesRepository;

    @Getter
    private final MutableLiveData<List<PlaceCategory>> placeCategoryData = new MutableLiveData<>();

    @Inject
    public PlacesViewModel(PlacesRepository placesRepository) {
        this.placesRepository = placesRepository;
        fetchPlaceCategoryData();
    }

    public void doOnPlaceCategoryClicked(PlaceCategory placeCategory) {
    }

    private void fetchPlaceCategoryData() {
        // List<PlaceCategory> data = repository.getPlaceCategoryData();
        // _placeCategoryData.postValue(data);

        // for now initialize with sample data
        List<PlaceCategory> data = new ArrayList<>(Arrays.asList(
                new PlaceCategory(1, "Category1", R.drawable.common_google_signin_btn_icon_light), // use android drawable res for now, deviate from android dependency later
                new PlaceCategory(2, "Category2", R.drawable.common_google_signin_btn_icon_dark),
                new PlaceCategory(3, "Category3", R.drawable.common_google_signin_btn_icon_light)
        ));
        placeCategoryData.postValue(data);
    }
}
