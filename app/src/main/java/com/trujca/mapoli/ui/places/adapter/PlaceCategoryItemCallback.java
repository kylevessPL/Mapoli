package com.trujca.mapoli.ui.places.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.trujca.mapoli.ui.places.model.PlaceCategory;

import java.util.Objects;

public class PlaceCategoryItemCallback extends DiffUtil.ItemCallback<PlaceCategory> {

    @Override
    public boolean areItemsTheSame(@NonNull final PlaceCategory oldItem, @NonNull final PlaceCategory newItem) {
        return Objects.equals(oldItem.getId(), newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull final PlaceCategory oldItem, @NonNull final PlaceCategory newItem) {
        return Objects.equals(oldItem, newItem);
    }
}
