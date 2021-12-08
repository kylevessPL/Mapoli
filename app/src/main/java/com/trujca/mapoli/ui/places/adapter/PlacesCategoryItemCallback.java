package com.trujca.mapoli.ui.places.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.trujca.mapoli.data.places.model.PlaceNearby;

import java.util.Objects;

public class PlacesCategoryItemCallback extends DiffUtil.ItemCallback<PlaceNearby> {

    @Override
    public boolean areItemsTheSame(@NonNull final PlaceNearby oldItem, @NonNull final PlaceNearby newItem) {
        return Objects.equals(oldItem.getPlaceId(), newItem.getPlaceId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull final PlaceNearby oldItem, @NonNull final PlaceNearby newItem) {
        return Objects.equals(oldItem, newItem);
    }
}
