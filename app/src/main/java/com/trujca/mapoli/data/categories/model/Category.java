package com.trujca.mapoli.data.categories.model;

import com.google.firebase.firestore.DocumentId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @DocumentId
    private String documentId;
    private String name;
}
