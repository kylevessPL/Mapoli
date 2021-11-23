package com.trujca.mapoli.ui.places.model;

import lombok.Value;

@Value
public class ChosenPlace
{
    int id;
    String name;
    float distance;

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }
}
