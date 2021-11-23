package com.trujca.mapoli.ui.places.view;

import static java.util.Objects.requireNonNull;

import com.trujca.mapoli.R;
import com.trujca.mapoli.databinding.FragmentPlacesBinding;
import com.trujca.mapoli.databinding.FragmentPlacesCategoryBinding;
import com.trujca.mapoli.ui.base.BaseFragment;
import com.trujca.mapoli.ui.places.adapter.ChosenPlaceAdapter;
import com.trujca.mapoli.ui.places.adapter.PlacesAdapter;
import com.trujca.mapoli.ui.places.model.ChosenPlace;
import com.trujca.mapoli.ui.places.model.PlaceCategory;
import com.trujca.mapoli.ui.places.viewmodel.PlacesCategoryViewModel;
import com.trujca.mapoli.ui.places.viewmodel.PlacesViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PlacesCategoryFragment extends BaseFragment<FragmentPlacesCategoryBinding, PlacesCategoryViewModel>
{
    @Override
    public Class<PlacesCategoryViewModel> getViewModelClass() {
        return PlacesCategoryViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_places_category;
    }

    @Override
    protected void setupView() {
        setupAdapter();
    }

    @Override
    protected void updateUI() {
        viewModel.getFoundPlaces().observe(getViewLifecycleOwner(), this::updateAdapterData);
    }

    private void setupAdapter() {
        binding.placeChosenCategoryView.setAdapter(new ChosenPlaceAdapter((view, item) -> {
            ChosenPlace category = (ChosenPlace) item;
            viewModel.doOnPlaceCategoryClicked(category);
        }));
    }

    private void updateAdapterData(final List<ChosenPlace> data) {
        if (data == null) {
            return;
        }
        requireNonNull((ChosenPlaceAdapter) binding.placeChosenCategoryView.getAdapter()).submitList(data);
    }

}
