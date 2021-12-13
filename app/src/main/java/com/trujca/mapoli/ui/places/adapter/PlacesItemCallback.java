package com.trujca.mapoli.ui.places.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.trujca.mapoli.data.places.model.PlaceCategory;

import java.util.Objects;

public class PlacesItemCallback extends DiffUtil.ItemCallback<PlaceCategory> {

    @Override
    public boolean areItemsTheSame(@NonNull final PlaceCategory oldItem, @NonNull final PlaceCategory newItem) {
        return Objects.equals(oldItem.getPlaceId(), newItem.getPlaceId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull final PlaceCategory oldItem, @NonNull final PlaceCategory newItem) {
        return Objects.equals(oldItem, newItem);
    }
}
