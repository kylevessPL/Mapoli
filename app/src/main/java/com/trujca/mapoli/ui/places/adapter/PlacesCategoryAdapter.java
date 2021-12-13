package com.trujca.mapoli.ui.places.adapter;

import com.trujca.mapoli.R;
import com.trujca.mapoli.data.places.model.PlaceNearby;
import com.trujca.mapoli.ui.base.BaseAdapter;
import com.trujca.mapoli.ui.common.RecyclerViewClickListener;

public class PlacesCategoryAdapter extends BaseAdapter<PlaceNearby> {

    public PlacesCategoryAdapter(RecyclerViewClickListener clickListener) {
        super(new PlacesCategoryItemCallback(), clickListener);
    }

    @Override
    protected int getLayoutIdForPosition(final int position) {
        return R.layout.item_places_category;
    }
}
