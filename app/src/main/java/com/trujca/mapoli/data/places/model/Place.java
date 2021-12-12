package com.trujca.mapoli.data.places.model;

import com.google.gson.annotations.SerializedName;
import com.trujca.mapoli.data.common.model.Coordinates;

import org.itishka.gsonflatten.Flatten;

import lombok.Value;

@Value
public class Place {
    @SerializedName("fsq_id")
    String placeId;
    String name;
    @Flatten("location::address_extended")
    String address;
    @SerializedName("tel")
    String phone;
    String email;
    String fax;
    @SerializedName("website")
    String websiteUrl;
    @Flatten("geocodes::main")
    Coordinates coordinates;
}
