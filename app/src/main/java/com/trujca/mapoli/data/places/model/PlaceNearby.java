package com.trujca.mapoli.data.places.model;

import com.google.gson.annotations.SerializedName;

import org.itishka.gsonflatten.Flatten;

import lombok.Value;

@Value
public class PlaceNearby {
    @SerializedName("fsq_id")
    String placeId;
    String name;
    @Flatten("location::address")
    String address;
    Integer distance;
    @Flatten("categories::0::icon::prefix")
    String iconUrl;
}
