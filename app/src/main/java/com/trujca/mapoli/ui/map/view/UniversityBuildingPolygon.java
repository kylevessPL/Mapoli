package com.trujca.mapoli.ui.map.view;

import static androidx.core.content.res.ResourcesCompat.getColor;

import com.trujca.mapoli.R;
import com.trujca.mapoli.data.common.model.Coordinates;
import com.trujca.mapoli.data.map.model.LodzUniversityBuilding;
import com.trujca.mapoli.databinding.UniversityBuildingInfoWindowBinding;
import com.trujca.mapoli.util.AppUtils;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

public class UniversityBuildingPolygon extends Polygon {

    @Getter
    private final LodzUniversityBuilding universityBuilding;

    public UniversityBuildingPolygon(final MapView mapView, final LodzUniversityBuilding universityBuilding) {
        super(mapView);
        this.universityBuilding = universityBuilding;
        setupPolygon(mapView);
        setupClickListener();
        setUniversityBuildingDetails();
    }

    private void setupPolygon(final MapView mapView) {
        getFillPaint().setColor(getColor(mapView.getResources(), R.color.sand_yellow, null));
        getOutlinePaint().setStrokeWidth(4);
        InfoWindow infoWindow = new MapInfoWindow<UniversityBuildingInfoWindowBinding>(mapView, R.layout.university_building_info_window);
        setInfoWindow(infoWindow);
    }

    private void setupClickListener() {
        Coordinates coordinates = Coordinates.centerFromGeometry(universityBuilding.getGeometry());
        setOnClickListener((view, map, eventPos) -> {
            AppUtils.navigateToPointOnMap(map, coordinates);
            InfoWindow.closeAllInfoWindowsOn(map);
            view.showInfoWindow();
            return true;
        });
    }

    private void setUniversityBuildingDetails() {
        setTitle(universityBuilding.getName());
        setPoints(createPolygonPoints());
    }

    private List<GeoPoint> createPolygonPoints() {
        return universityBuilding.getGeometry()
                .stream()
                .map(coordinates -> new GeoPoint(coordinates[1], coordinates[0]))
                .collect(Collectors.toList());
    }
}
