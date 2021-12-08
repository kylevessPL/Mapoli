package com.trujca.mapoli.ui.places.view;

import androidx.navigation.Navigation;

import com.trujca.mapoli.R;
import com.trujca.mapoli.data.places.model.PlaceCategory;
import com.trujca.mapoli.databinding.FragmentPlacesBinding;
import com.trujca.mapoli.ui.base.BaseFragment;
import com.trujca.mapoli.ui.places.adapter.PlacesAdapter;
import com.trujca.mapoli.ui.places.view.PlacesFragmentDirections.ActionPlacesFragmentToPlacesCategoryFragment;
import com.trujca.mapoli.ui.places.viewmodel.PlacesViewModel;

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
        viewModel.getNavigateToPlacesCategoryFragment().observe(getViewLifecycleOwner(), this::navigateToPlacesCategoryFragment);
    }

    private void setupAdapter() {
        PlacesAdapter adapter = new PlacesAdapter((view, item) -> {
            PlaceCategory category = (PlaceCategory) item;
            viewModel.navigateToPlacesCategoryFragment(category);
        });
        adapter.submitList(viewModel.getPlaceCategories());
        binding.recyclerView.setAdapter(adapter);
    }

    private void navigateToPlacesCategoryFragment(Integer categoryId) {
        if (categoryId != null) {
            ActionPlacesFragmentToPlacesCategoryFragment action = PlacesFragmentDirections
                    .actionPlacesFragmentToPlacesCategoryFragment(categoryId);
            Navigation.findNavController(requireView()).navigate(action);
        }
    }
}
