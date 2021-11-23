package com.trujca.mapoli.ui.places.adapter;

import com.trujca.mapoli.R;
import com.trujca.mapoli.ui.base.BaseAdapter;
import com.trujca.mapoli.ui.common.RecyclerViewClickListener;
import com.trujca.mapoli.ui.places.model.ChosenPlace;

public class ChosenPlaceAdapter extends BaseAdapter<ChosenPlace>
{

    public ChosenPlaceAdapter(RecyclerViewClickListener recyclerViewClickListener)
    {
        super(new ChosenPlaceItemCallback(), recyclerViewClickListener);
    }

    @Override
    protected int getLayoutIdForPosition(final int position)
    {
        return R.layout.item_places_category;
    }
}