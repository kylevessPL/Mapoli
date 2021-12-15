package com.trujca.mapoli.data.common.model;

import androidx.annotation.NonNull;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Coordinates {

    private Float latitude;
    private Float longitude;

    public static Coordinates centerFromGeometry(List<float[]> geometry) {
        float x = 0;
        float y = 0;
        for (float[] coordinate : geometry) {
            x += coordinate[1];
            y += coordinate[0];
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
