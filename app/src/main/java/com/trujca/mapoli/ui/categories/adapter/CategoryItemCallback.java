package com.trujca.mapoli.ui.categories.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.trujca.mapoli.ui.categories.model.Category;

import java.util.Objects;

public class CategoryItemCallback extends DiffUtil.ItemCallback<Category> {

    @Override
    public boolean areItemsTheSame(@NonNull final Category oldItem, @NonNull final Category newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull final Category oldItem, @NonNull final Category newItem) {
        return Objects.equals(oldItem.getName(), newItem.getName());
    }
}