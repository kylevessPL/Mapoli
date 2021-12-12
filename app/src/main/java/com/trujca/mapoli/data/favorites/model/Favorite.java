package com.trujca.mapoli.data.favorites.model;

import com.google.firebase.firestore.DocumentId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Favorite {
    @DocumentId
    private String documentId;
    private String name;
    private Float coordX;
    private Float coordY;
}
