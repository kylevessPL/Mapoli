package com.trujca.mapoli.data.map.repository;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.trujca.mapoli.data.map.model.LodzUniversityBuilding;

import org.itishka.gsonflatten.FlattenTypeAdapterFactory;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LodzUniversityBuildingListDeserializer implements JsonDeserializer<List<LodzUniversityBuilding>> {

    @Override
    public List<LodzUniversityBuilding> deserialize(final JsonElement json, final Type type, final JsonDeserializationContext context) throws JsonParseException {
        JsonArray content = new JsonArray();
        Set<Map.Entry<String, JsonElement>> entries = json.getAsJsonObject().entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            content.add(entry.getValue());
        }
        return new GsonBuilder()
                .registerTypeAdapterFactory(new FlattenTypeAdapterFactory())
                .create()
                .fromJson(content, type);
    }
}
