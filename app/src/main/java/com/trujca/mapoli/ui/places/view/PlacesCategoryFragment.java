package com.trujca.mapoli.ui.places.view;

import static android.widget.Toast.LENGTH_LONG;
import static java.util.Objects.requireNonNull;

import android.widget.Toast;

import androidx.navigation.Navigation;

import com.trujca.mapoli.R;
import com.trujca.mapoli.data.places.model.PlaceNearby;
import com.trujca.mapoli.databinding.FragmentPlacesCategoryBinding;
import com.trujca.mapoli.ui.base.BaseFragment;
import com.trujca.mapoli.ui.places.adapter.PlacesCategoryAdapter;
import com.trujca.mapoli.ui.places.viewmodel.PlacesCategoryViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PlacesCategoryFragment extends BaseFragment<FragmentPlacesCategoryBinding, PlacesCategoryViewModel> {

    @Override
    public Class<PlacesCategoryViewModel> getViewModelClass() {
        return PlacesCategoryViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_places_category;
    }

    @Override
    protected int getTitle() {
        return PlacesCategoryFragmentArgs.fromBundle(getArguments()).getCategoryName();
    }

    @Override
    protected void setupView() {
        setupAdapter();
        fetchPlaces();
    }

    @Override
    protected void updateUI() {
        viewModel.getPlacesNearby().observe(getViewLifecycleOwner(), this::updateAdapterData);
        viewModel.getGeneralError().observe(getViewLifecycleOwner(), this::showGeneralErrorMessage);
        viewModel.getNavigateToMapFragment().observe(getViewLifecycleOwner(), this::navigateToMapFragment);
    }

    private void setupAdapter() {
        binding.recyclerView.setAdapter(new PlacesCategoryAdapter((view, item) -> {
            PlaceNearby placeNearby = (PlaceNearby) item;
            viewModel.handleItemClicked(placeNearby);
        }));
    }

    private void updateAdapterData(final List<PlaceNearby> data) {
        if (data == null) {
            return;
        }
        requireNonNull((PlacesCategoryAdapter) binding.recyclerView.getAdapter()).submitList(new ArrayList<>(data));
    }

    private void showGeneralErrorMessage(final Boolean value) {
        Toast.makeText(getContext(), getString(R.string.general_error_message), LENGTH_LONG).show();
    }

    private void navigateToMapFragment(String placeId) {
        if (placeId != null) {
            PlacesCategoryFragmentDirections.ActionPlacesCategoryFragmentToMapFragment action = PlacesCategoryFragmentDirections
                    .actionPlacesCategoryFragmentToMapFragment().setPlaceId(placeId);
            Navigation.findNavController(requireView()).navigate(action);
        }
    }

    private void fetchPlaces() {
        Integer categoryId = PlacesCategoryFragmentArgs.fromBundle(getArguments()).getCategoryId();
        viewModel.fetchPlacesInCategory(categoryId);
    }
}
