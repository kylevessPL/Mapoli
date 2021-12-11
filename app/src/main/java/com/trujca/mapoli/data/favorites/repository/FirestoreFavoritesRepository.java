package com.trujca.mapoli.data.favorites.repository;

import static java.util.Objects.requireNonNull;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.trujca.mapoli.data.favorites.model.Favorite;
import com.trujca.mapoli.data.util.RepositoryCallback;

import java.util.List;

import javax.inject.Inject;

public class FirestoreFavoritesRepository implements FavoritesRepository {

    private static final String TAG = FirestoreFavoritesRepository.class.getSimpleName();

    private final FirebaseAuth auth;
    private final FirebaseFirestore firestore;

    @Inject
    public FirestoreFavoritesRepository(FirebaseAuth auth, FirebaseFirestore firestore) {
        this.auth = auth;
        this.firestore = firestore;
    }

    @Override
    public void getAllFavorites(RepositoryCallback<List<Favorite>, Void> callback) {
        firestore
                .collection("users")
                .document(requireNonNull(auth.getUid()))
                .collection("favorites")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Favorite> favorites = task.getResult().toObjects(Favorite.class);
                        callback.onSuccess(favorites);
                        Log.w(TAG, "getAllFavorites:success");
                    } else {
                        Exception ex = requireNonNull(task.getException());
                        callback.onError(null);
                        Log.w(TAG, "getAllFavorites:failure", ex);
                    }
                });
    }

    @Override
    public void addFavorite(final Favorite favorite, RepositoryCallback<Void, Void> callback) {
        firestore
                .collection("users")
                .document(requireNonNull(auth.getUid()))
                .collection("favorites")
                .document(favorite.getDocumentId())
                .set(favorite)
                .addOnSuccessListener(nothing -> {
                    callback.onSuccess(null);
                    Log.d(TAG, "addFavorite:success");
                })
                .addOnFailureListener(ex -> {
                    callback.onError(null);
                    Log.w(TAG, "addFavorite:failure", ex);
                });
    }

    @Override
    public void deleteFavorite(String documentId, RepositoryCallback<Void, Void> callback) {
        // TODO:
    }
}
