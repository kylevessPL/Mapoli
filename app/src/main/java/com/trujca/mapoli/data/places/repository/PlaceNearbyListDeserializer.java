package com.trujca.mapoli.data.places.repository;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.trujca.mapoli.data.places.model.PlaceNearby;

import org.itishka.gsonflatten.FlattenTypeAdapterFactory;

import java.lang.reflect.Type;
import java.util.List;

public class PlaceNearbyListDeserializer implements JsonDeserializer<List<PlaceNearby>> {

    @Override
    public List<PlaceNearby> deserialize(final JsonElement json, final Type type, final JsonDeserializationContext context) throws JsonParseException {
        JsonElement content = json.getAsJsonObject().get("results");
        return new GsonBuilder()
                .registerTypeAdapterFactory(new FlattenTypeAdapterFactory())
                .create()
                .fromJson(content, type);
    }
}
