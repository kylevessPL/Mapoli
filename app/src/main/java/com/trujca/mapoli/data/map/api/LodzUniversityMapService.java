package com.trujca.mapoli.data.map.api;

import com.trujca.mapoli.data.map.model.LodzUniversityBuilding;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LodzUniversityMapService {

    @GET("buildings.json")
    Call<List<LodzUniversityBuilding>> getBuildings();
}
