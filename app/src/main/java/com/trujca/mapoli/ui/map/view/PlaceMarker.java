package com.trujca.mapoli.ui.map.view;

import static androidx.core.content.res.ResourcesCompat.getColor;
import static androidx.core.content.res.ResourcesCompat.getDrawable;
import static java.util.Objects.requireNonNull;

import android.graphics.drawable.Drawable;

import androidx.annotation.LayoutRes;
import androidx.databinding.ViewDataBinding;

import com.trujca.mapoli.R;
import com.trujca.mapoli.data.common.model.Coordinates;
import com.trujca.mapoli.util.AppUtils;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

import lombok.Getter;

public class PlaceMarker<DB extends ViewDataBinding> extends Marker {

    @Getter
    private final Object item;

    public PlaceMarker(final MapView mapView, @LayoutRes final int layoutRes, final Object item, String name, Coordinates position) {
        super(mapView);
        this.item = item;
        setupMarker(mapView, layoutRes);
        setDetails(name, position);
        setupClickListener(position);
    }

    private void setupMarker(final MapView mapView, @LayoutRes final int layoutRes) {
        setIcon(getMarkerDrawable(mapView));
        InfoWindow infoWindow = new MapInfoWindow<DB>(mapView, layoutRes);
        setInfoWindow(infoWindow);
    }

    private void setupClickListener(Coordinates position) {
        setOnMarkerClickListener((view, map) -> {
            AppUtils.navigateToPointOnMap(map, position);
            InfoWindow.closeAllInfoWindowsOn(map);
            view.showInfoWindow();
            return true;
        });
    }

    private void setDetails(String name, Coordinates position) {
        setTitle(name);
        setPosition(new GeoPoint(position.getLatitude(), position.getLongitude()));
    }

    private Drawable getMarkerDrawable(final MapView mapView) {
        Drawable icon = getDrawable(mapView.getResources(), R.drawable.ic_place_48, null);
        requireNonNull(icon).setTint(getColor(mapView.getResources(), R.color.md_red_600, null));
        return icon;
    }
}
