package com.trujca.mapoli.ui.places.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.trujca.mapoli.data.places.repository.FoursquareService;
import com.trujca.mapoli.data.places.repository.PlacesRepository;
import com.trujca.mapoli.ui.places.model.ChosenPlace;
import com.trujca.mapoli.ui.places.model.PlaceCategory;
import com.trujca.mapoli.util.Constants;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@HiltViewModel
public class PlacesCategoryViewModel extends ViewModel
{
    @Getter
    private final MutableLiveData<PlaceCategory> chosenPlaceCategory = new MutableLiveData<PlaceCategory>();
    @Getter
    private final MutableLiveData<List<ChosenPlace>> foundPlaces = new MutableLiveData<List<ChosenPlace>>();

    @Inject
    public PlacesCategoryViewModel() {
        getPlacesData();
    }

    public void doOnPlaceCategoryClicked(ChosenPlace chosenPlace) {
        //todo: poka na mapie punkta czy tam trase github copilot pls napisz essa
    }

    private void getPlacesData()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.foursquare.com/v3/places/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FoursquareService service = retrofit.create(FoursquareService.class);

        String coordinates = Constants.LATITUDE_INITIAL + "," + Constants.LONGTITUDE_INITIAL; //todo: na razie punkt z mapy, ma być bieżaca lokalizacja
//chosenPlaceCategory.getValue().getName()

        Call<JsonObject> placeJsonData = service.getChosenPlace( coordinates, "restaurant");
        List<ChosenPlace> data = new ArrayList<>();

        placeJsonData.enqueue(new Callback<JsonObject>()
        {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
            {
                JsonObject mainObject = response.body();
                JsonArray results = mainObject.getAsJsonArray("results");
                for(int i = 0; i < results.size();i++)
                {
                    JsonObject result = (JsonObject) results.get(i);
                    String name = result.get("name").getAsString();
                    int distance = result.get("distance").getAsInt();
                    data.add(new ChosenPlace(i,name,distance));
                }
                foundPlaces.postValue(data);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t)
            {

            }
        });

    }

}
