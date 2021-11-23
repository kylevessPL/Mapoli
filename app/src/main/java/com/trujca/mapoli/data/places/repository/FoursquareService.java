package com.trujca.mapoli.data.places.repository;

import com.google.gson.JsonObject;
import com.trujca.mapoli.R;
import com.trujca.mapoli.ui.places.model.ChosenPlace;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface FoursquareService
{
    @Headers("Authorization: fsq3WVAkdVbEb1oGSEedwSRBZmsHsD0NUyhuTIQuGb2V1Y4=")// +  R.string.foursquare_api_key}) //jak dawałem niejawnie to nie działało xd
    @GET("nearby")
    Call<JsonObject> getChosenPlace(@Query("ll") String coordinates, @Query("query") String placeName);
}
