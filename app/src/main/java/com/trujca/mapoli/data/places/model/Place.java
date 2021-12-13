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
    @SerializedName("location")
    Address address;
    @SerializedName("tel")
    String phone;
    String email;
    @SerializedName("website")
    String websiteUrl;
    @Flatten("geocodes::main")
    Coordinates coordinates;

    @Value
    public static class Address {
        String address;
        @SerializedName("postcode")
        String zipCode;
        @SerializedName("locality")
        String city;
    }
}
