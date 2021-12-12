package com.trujca.mapoli.ui.places.adapter;

import com.trujca.mapoli.R;
import com.trujca.mapoli.data.places.model.PlaceCategory;
import com.trujca.mapoli.ui.base.BaseAdapter;
import com.trujca.mapoli.ui.common.RecyclerViewClickListener;

public class PlacesAdapter extends BaseAdapter<PlaceCategory> {

    public PlacesAdapter(RecyclerViewClickListener clickListener) {
        super(new PlacesItemCallback(), clickListener);
    }

    @Override
    protected int getLayoutIdForPosition(final int position) {
        return R.layout.item_places;
    }
}
