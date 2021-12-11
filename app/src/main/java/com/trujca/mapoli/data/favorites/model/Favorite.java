package com.trujca.mapoli.data.favorites.model;

import com.google.firebase.firestore.DocumentId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Favorite {
    @DocumentId
    private String documentId;
    private String name;
    private Float coordX;
    private Float coordY;

    public String getName(){
        return name;
    }

    public float getX(){
        return coordX;
    }

    public float getY(){
        return coordY;
    }
}
