package com.trujca.mapoli.ui.map.view;

import static androidx.core.content.res.ResourcesCompat.getColor;
import static androidx.core.content.res.ResourcesCompat.getDrawable;
import static java.util.Objects.requireNonNull;

import android.graphics.drawable.Drawable;

import com.trujca.mapoli.R;
import com.trujca.mapoli.data.places.model.Place;
import com.trujca.mapoli.databinding.PlaceInfoWindowBinding;
import com.trujca.mapoli.util.AppUtils;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

import lombok.Getter;

public class PlaceMarker extends Marker {

    @Getter
    private final Place place;

    public PlaceMarker(final MapView mapView, final Place place) {
        super(mapView);
        this.place = place;
        setupMarker(mapView);
        setupClickListener();
        setPlaceDetails();
    }

    private void setupMarker(final MapView mapView) {
        setIcon(getMarkerDrawable(mapView));
        InfoWindow infoWindow = new MapInfoWindow<PlaceInfoWindowBinding>(mapView, R.layout.place_info_window);
        setInfoWindow(infoWindow);
    }

    private void setupClickListener() {
        setOnMarkerClickListener((view, map) -> {
            AppUtils.navigateToPointOnMap(map, place.getCoordinates());
            InfoWindow.closeAllInfoWindowsOn(map);
            view.showInfoWindow();
            return true;
        });
    }

    private void setPlaceDetails() {
        setTitle(place.getName());
        setPosition(new GeoPoint(
                place.getCoordinates().getLatitude(),
                place.getCoordinates().getLongitude()
        ));
    }

    private Drawable getMarkerDrawable(final MapView mapView) {
        Drawable icon = getDrawable(mapView.getResources(), R.drawable.ic_place_48, null);
        requireNonNull(icon).setTint(getColor(mapView.getResources(), R.color.md_red_600, null));
        return icon;
    }
}
