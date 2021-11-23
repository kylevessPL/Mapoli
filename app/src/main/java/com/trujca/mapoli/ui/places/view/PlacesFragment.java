package com.trujca.mapoli.ui.places.view;

import static java.util.Objects.requireNonNull;



import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.trujca.mapoli.R;
import com.trujca.mapoli.databinding.FragmentPlacesBinding;
import com.trujca.mapoli.ui.base.BaseFragment;
import com.trujca.mapoli.ui.places.adapter.PlacesAdapter;
import com.trujca.mapoli.ui.places.model.PlaceCategory;
import com.trujca.mapoli.ui.places.viewmodel.PlacesCategoryViewModel;
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
        viewModel.getNavigateToNewFragment().observe(getViewLifecycleOwner(), this::changeFragment);
    }

    private void changeFragment(Boolean b)
    {
        Navigation.findNavController(getView()).navigate(R.id.chosen_place);
    }


    private void setupAdapter() {
        binding.placeCategoriesView.setAdapter(new PlacesAdapter((view, item) -> {
            PlaceCategory category = (PlaceCategory) item;
            viewModel.doOnPlaceCategoryClicked(category);
        }));
    }

    private void updateAdapterData(final List<PlaceCategory> data) {
        if (data == null) {
            return;
        }
        requireNonNull((PlacesAdapter) binding.placeCategoriesView.getAdapter()).submitList(data);
    }
}
