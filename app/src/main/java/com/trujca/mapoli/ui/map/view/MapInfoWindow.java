package com.trujca.mapoli.ui.map.view;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewTreeLifecycleOwner;

import com.trujca.mapoli.BR;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

public class MapInfoWindow<DB extends ViewDataBinding> extends InfoWindow {

    protected DB binding;

    public MapInfoWindow(final MapView mapView, @LayoutRes final int layoutRes) {
        super(
                DataBindingUtil.inflate(
                        (LayoutInflater) mapView.getContext().getSystemService(LAYOUT_INFLATER_SERVICE),
                        layoutRes,
                        (ViewGroup) mapView.getParent(),
                        false
                ).getRoot(),
                mapView
        );
        binding = DataBindingUtil.getBinding(mView);
    }

    @Override
    public void onOpen(final Object item) {
        binding.setLifecycleOwner(ViewTreeLifecycleOwner.get(mView));
        binding.setVariable(BR.item, item);
    }

    @Override
    public void onClose() {
    }

    @Override
    public void onDetach() {
        super.onDetach();
        binding = null;
    }
}
