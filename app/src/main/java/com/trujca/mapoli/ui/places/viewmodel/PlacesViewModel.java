package com.trujca.mapoli.ui.places.viewmodel;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.trujca.mapoli.R;
import com.trujca.mapoli.data.places.repository.PlacesRepository;
import com.trujca.mapoli.ui.places.model.PlaceCategory;
import com.trujca.mapoli.ui.places.view.PlacesCategoryFragment;

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
    @Getter
    private final MutableLiveData<PlaceCategory> chosenPlaceCategory = new MutableLiveData<PlaceCategory>();
    @Getter
    private final MutableLiveData<Boolean> navigateToNewFragment = new MutableLiveData<>();
    @Inject
    public PlacesViewModel(PlacesRepository placesRepository) {
        this.placesRepository = placesRepository;
        fetchPlaceCategoryData();
    }

    public void doOnPlaceCategoryClicked(PlaceCategory placeCategory) {
        chosenPlaceCategory.setValue(placeCategory);
        navigateToNewFragment.postValue(true);
        System.out.println(chosenPlaceCategory.getValue());
    }

    private void fetchPlaceCategoryData() {
        // List<PlaceCategory> data = repository.getPlaceCategoryData();
        // _placeCategoryData.postValue(data);

        // for now initialize with sample data
        List<PlaceCategory> data = new ArrayList<>(Arrays.asList(
                new PlaceCategory(1, "Restauracje", R.drawable.ic_baseline_restaurant_24), // use android drawable res for now, deviate from android dependency later
                new PlaceCategory(2, "Stacje paliw", R.drawable.ic_baseline_local_gas_station_24),
                new PlaceCategory(3, "Sklepy", R.drawable.ic_baseline_shopping_cart_24)
        ));
        placeCategoryData.postValue(data);
    }
}
