package com.trujca.mapoli.ui.category.view;

import static android.content.ContentValues.TAG;
import static java.util.Objects.requireNonNull;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.trujca.mapoli.R;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {
    CategoryListAdapter itemsAdapter;
    ArrayList<String> categories;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public void refreshData() {

        getCategories();
        itemsAdapter.notifyDataSetChanged();
    }

    public void getCategories() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        categories.clear();
        db.collection("categories")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : requireNonNull(task.getResult())) {
                            categories.add(document.getData().get("name").toString());
                            Log.d(TAG, document.getId() + " => " + document.getData());
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        AddCategoryDialog dialog = new AddCategoryDialog();
        dialog.show(getChildFragmentManager(), "categoryDialog");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.action_add_category) {
            showNoticeDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_favourites).setVisible(false);
        menu.findItem(R.id.action_add_category).setVisible(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        categories = new ArrayList<>();
        getCategories();
        return inflater.inflate(R.layout.fragment_category, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView categoryListView = view.findViewById(R.id.categoryList);
        itemsAdapter = new CategoryListAdapter(categories);
        categoryListView.setAdapter(itemsAdapter);
        categoryListView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
