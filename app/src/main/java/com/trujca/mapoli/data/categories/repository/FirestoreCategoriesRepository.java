package com.trujca.mapoli.data.categories.repository;

import static java.util.Objects.requireNonNull;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.trujca.mapoli.data.categories.model.Category;
import com.trujca.mapoli.data.util.RepositoryCallback;

import java.util.List;

import javax.inject.Inject;

public class FirestoreCategoriesRepository implements CategoriesRepository {

    private static final String TAG = FirestoreCategoriesRepository.class.getSimpleName();

    private final FirebaseAuth auth;
    private final FirebaseFirestore firestore;

    @Inject
    public FirestoreCategoriesRepository(FirebaseAuth auth, FirebaseFirestore firestore) {
        this.auth = auth;
        this.firestore = firestore;
    }

    @Override
    public void getAllCategories(RepositoryCallback<List<Category>, Void> callback) {
        callback.onLoading(true);
        firestore.collection("users").document(requireNonNull(auth.getUid())).collection("categories")
                .get()
                .addOnCompleteListener(task -> callback.onLoading(false))
                .addOnSuccessListener(documents -> {
                    List<Category> categories = documents.toObjects(Category.class);
                    callback.onSuccess(categories);
                    Log.d(TAG, "getAllCategories:success");
                })
                .addOnFailureListener(ex -> {
                    callback.onError(null);
                    Log.w(TAG, "getAllCategories:failure", ex);
                });
    }

    @Override
    public void addCategory(final Category category, RepositoryCallback<Void, Void> callback) {
        callback.onLoading(true);
        firestore
                .collection("users")
                .document(requireNonNull(auth.getUid()))
                .collection("categories")
                .document(category.getDocumentId())
                .set(category)
                .addOnCompleteListener(task -> callback.onLoading(false))
                .addOnSuccessListener(nothing -> {
                    callback.onSuccess(null);
                    Log.d(TAG, "addCategory:success");
                })
                .addOnFailureListener(ex -> {
                    callback.onError(null);
                    Log.w(TAG, "addCategory:failure", ex);
                });
    }
}
