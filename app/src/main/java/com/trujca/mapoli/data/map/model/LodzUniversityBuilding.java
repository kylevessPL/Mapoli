package com.trujca.mapoli.data.map.model;

import java.util.List;

import lombok.Value;

@Value
public class LodzUniversityBuilding {
    String name;
    String address;
    List<float[]> geometry;
}
