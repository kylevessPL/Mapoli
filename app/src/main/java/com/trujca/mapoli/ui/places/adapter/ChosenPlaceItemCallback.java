package com.trujca.mapoli.ui.places.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.trujca.mapoli.ui.places.model.ChosenPlace;
import com.trujca.mapoli.ui.places.model.PlaceCategory;

import java.util.Objects;

public class ChosenPlaceItemCallback extends DiffUtil.ItemCallback<ChosenPlace> {

    @Override
    public boolean areItemsTheSame(@NonNull final ChosenPlace oldItem, @NonNull final ChosenPlace newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull final ChosenPlace oldItem, @NonNull final ChosenPlace newItem) {
        return Objects.equals(oldItem.getName(), newItem.getName());
    }
}
