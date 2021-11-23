package com.trujca.mapoli.ui.categories.view;

import static java.util.Objects.requireNonNull;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.trujca.mapoli.R;
import com.trujca.mapoli.databinding.FragmentCategoriesBinding;
import com.trujca.mapoli.ui.base.BaseFragment;
import com.trujca.mapoli.ui.categories.adapter.CategoriesAdapter;
import com.trujca.mapoli.ui.categories.model.Category;
import com.trujca.mapoli.ui.categories.viewmodel.CategoriesViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CategoriesFragment extends BaseFragment<FragmentCategoriesBinding, CategoriesViewModel> {

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_favourites).setVisible(false);
        menu.findItem(R.id.action_add_category).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_category) {
            showAddCategoryDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Class<CategoriesViewModel> getViewModelClass() {
        return CategoriesViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_categories;
    }

    @Override
    protected void setupView() {
        setHasOptionsMenu(true);
        setupAdapter();
    }

    @Override
    protected void updateUI() {
        viewModel.getCategoriesData().observe(getViewLifecycleOwner(), this::updateAdapterData);
    }

    private void setupAdapter() {
        binding.categoriesView.setAdapter(new CategoriesAdapter((view, item) -> {
            Category category = (Category) item;
            viewModel.doOnPlaceCategoryClicked(category);
            Toast.makeText(getContext(), String.format("Item %s clicked!", category.getId()), Toast.LENGTH_SHORT).show();
        }));
    }

    private void updateAdapterData(final List<Category> data) {
        if (data == null) {
            return;
        }
        requireNonNull((CategoriesAdapter) binding.categoriesView.getAdapter()).submitList(data);
    }

    private void showAddCategoryDialog() {
        AddCategoryDialogFragment dialog = new AddCategoryDialogFragment();
        dialog.show(getChildFragmentManager(), AddCategoryDialogFragment.TAG);
    }
}
