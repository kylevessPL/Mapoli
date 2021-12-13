package com.trujca.mapoli.data.places.api;

import com.trujca.mapoli.data.common.model.Coordinates;
import com.trujca.mapoli.data.places.model.Place;
import com.trujca.mapoli.data.places.model.PlaceNearby;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FoursquarePlacesService {

    @GET("{fsq_id}?fields=fsq_id,name,location,tel,email,fax,website,geocodes")
    Call<Place> getPlaceDetails(@Path("fsq_id") String placeId);

    @GET("search?fields=fsq_id,name,location,distance,categories&sort=distance")
    Call<List<PlaceNearby>> getPlacesNearby(
            @Query("ll") Coordinates coordinates,
            @Query("radius") Integer radius,
            @Query("categories") Integer categoryId,
            @Query("limit") Integer limit
    );
}
