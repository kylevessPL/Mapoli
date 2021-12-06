package com.trujca.mapoli.data.categories.repository;

import static java.util.Objects.requireNonNull;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.trujca.mapoli.data.util.RepositoryCallback;
import com.trujca.mapoli.ui.categories.model.Category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

public class FirestoreCategoriesRepository implements CategoriesRepository {

    private static final String TAG = FirestoreCategoriesRepository.class.getSimpleName();

    private final FirebaseFirestore firestore;

    @Inject
    public FirestoreCategoriesRepository(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public void getAllCategories(RepositoryCallback<List<Category>, Void> callback) {
        List<Category> categories = new ArrayList<>();



        firestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("categories")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : requireNonNull(task.getResult())) {
                            if(!document.getData().get("name").toString().equals(""))
                            {
                                categories.add(new Category(UUID.randomUUID(), requireNonNull(document.getData().get("name")).toString()));
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        }
                        callback.onSuccess(categories);
                        Log.w(TAG, "getAllCategories:success");
                    } else {
                        Exception ex = requireNonNull(task.getException());
                        callback.onError(null);
                        Log.w(TAG, "getAllCategories:failure", ex);
                    }
                });
    }

    @Override
    public void addCategory(final Category category, RepositoryCallback<Void, Void> callback) {
        firestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("categories").document(category.getName())
                .set(Collections.singletonMap("name", category.getName()))
                .addOnSuccessListener(nothing -> {
                    callback.onSuccess(null);
                    Log.d(TAG, "addCategory:success");
                })
                .addOnFailureListener(ex -> Log.w(TAG, "addCategory:failure", ex));
    }
}
