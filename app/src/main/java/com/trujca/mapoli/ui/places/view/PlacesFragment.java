package com.trujca.mapoli.ui.places.view;

import static java.util.Objects.requireNonNull;

import android.widget.Toast;

import com.trujca.mapoli.R;
import com.trujca.mapoli.databinding.FragmentPlacesBinding;
import com.trujca.mapoli.ui.base.BaseFragment;
import com.trujca.mapoli.ui.places.adapter.PlacesAdapter;
import com.trujca.mapoli.ui.places.model.PlaceCategory;
import com.trujca.mapoli.ui.places.viewmodel.PlacesViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PlacesFragment extends BaseFragment<FragmentPlacesBinding, PlacesViewModel> {

    @Override
    public Class<PlacesViewModel> getViewModelClass() {
        return PlacesViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_places;
    }

    @Override
    protected void setupView() {
        setupAdapter();
    }

    @Override
    protected void updateUI() {
        viewModel.getPlaceCategoryData().observe(getViewLifecycleOwner(), this::updateAdapterData);
    }

    private void setupAdapter() {
        binding.placeCategoriesView.setAdapter(new PlacesAdapter((view, item) -> {
            PlaceCategory category = (PlaceCategory) item;
            viewModel.doOnPlaceCategoryClicked(category);
            Toast.makeText(getContext(), String.format("Item %s clicked!", category.getId()), Toast.LENGTH_SHORT).show();
        }));
    }

    private void updateAdapterData(final List<PlaceCategory> data) {
        if (data == null) {
            return;
        }
        requireNonNull((PlacesAdapter) binding.placeCategoriesView.getAdapter()).submitList(data);
    }
}
