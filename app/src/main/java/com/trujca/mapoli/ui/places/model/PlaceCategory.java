package com.trujca.mapoli.ui.places.model;

import androidx.annotation.IntegerRes;

import lombok.Value;

@Value
public class PlaceCategory {

    int id;
    String name;
    @IntegerRes
    int iconResource;
}
