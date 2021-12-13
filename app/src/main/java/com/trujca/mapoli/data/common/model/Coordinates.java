package com.trujca.mapoli.data.common.model;

import androidx.annotation.NonNull;

import java.util.List;

import lombok.Value;

@Value
public class Coordinates {
    Float latitude;
    Float longitude;

    public static Coordinates centerFromGeometry(List<float[]> geometry) {
        float x = 0;
        float y = 0;
        for (float[] coordinate : geometry) {
            x += coordinate[0];
            y += coordinate[1];
        }
        x = x / geometry.size();
        y = y / geometry.size();
        return new Coordinates(x, y);
    }

    @NonNull
    public String toString() {
        return latitude + "," + longitude;
    }
}
