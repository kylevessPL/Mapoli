package com.trujca.mapoli.data.common.model;

import androidx.annotation.NonNull;

import lombok.Value;

@Value
public class Coordinates {
    Float latitude;
    Float longtitude;

    @NonNull
    public String toString() {
        return this.getLatitude() + "," + this.getLongtitude();
    }
}
