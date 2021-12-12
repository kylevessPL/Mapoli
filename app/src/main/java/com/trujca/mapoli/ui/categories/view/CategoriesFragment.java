package com.trujca.mapoli.ui.categories.view;

import static android.widget.Toast.LENGTH_SHORT;
import static java.util.Objects.requireNonNull;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.trujca.mapoli.R;
import com.trujca.mapoli.data.categories.model.Category;
import com.trujca.mapoli.databinding.FragmentCategoriesBinding;
import com.trujca.mapoli.ui.base.BaseFragment;
import com.trujca.mapoli.ui.categories.adapter.CategoriesAdapter;
import com.trujca.mapoli.ui.categories.viewmodel.CategoriesViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CategoriesFragment extends BaseFragment<FragmentCategoriesBinding, CategoriesViewModel> {

    @Override
    public void onCreateOptionsMenu(@NonNull final Menu menu, @NonNull final MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_categories_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
    protected int getTitle() {
        return R.string.categories;
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
            Toast.makeText(getContext(), String.format("Item %s clicked!", category.getDocumentId()), LENGTH_SHORT).show();
        }));
    }

    private void updateAdapterData(final List<Category> data) {
        if (data == null) {
            return;
        }
        requireNonNull((CategoriesAdapter) binding.categoriesView.getAdapter()).submitList(new ArrayList<>(data));
    }

    private void showAddCategoryDialog() {
        AddCategoryDialogFragment dialog = new AddCategoryDialogFragment();
        dialog.show(getChildFragmentManager(), AddCategoryDialogFragment.TAG);
    }
}
