package com.trujca.mapoli.ui.categories.adapter;

import com.trujca.mapoli.R;
import com.trujca.mapoli.data.categories.model.Category;
import com.trujca.mapoli.ui.base.BaseAdapter;
import com.trujca.mapoli.ui.common.RecyclerViewClickListener;

public class CategoriesAdapter extends BaseAdapter<Category> {

    public CategoriesAdapter(RecyclerViewClickListener recyclerViewClickListener) {
        super(new CategoryItemCallback(), recyclerViewClickListener);
    }

    @Override
    protected int getLayoutIdForPosition(final int position) {
        return R.layout.item_category;
    }
}
