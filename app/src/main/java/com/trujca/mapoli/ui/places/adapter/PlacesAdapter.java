package com.trujca.mapoli.ui.places.adapter;

import com.trujca.mapoli.R;
import com.trujca.mapoli.ui.base.BaseAdapter;
import com.trujca.mapoli.ui.common.RecyclerViewClickListener;
import com.trujca.mapoli.ui.places.model.PlaceCategory;

public class PlacesAdapter extends BaseAdapter<PlaceCategory> {

    public PlacesAdapter(RecyclerViewClickListener recyclerViewClickListener) {
        super(new PlaceCategoryItemCallback(), recyclerViewClickListener);
    }

    @Override
    protected int getLayoutIdForPosition(final int position) {
        return R.layout.item_places_category;
    }
}
