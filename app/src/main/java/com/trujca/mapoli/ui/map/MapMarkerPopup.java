package com.trujca.mapoli.ui.map;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.trujca.mapoli.R;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;
import org.w3c.dom.Text;

public class MapMarkerPopup extends MarkerInfoWindow {

    /**
     * @param layoutResId layout that must contain these ids: bubble_title,bubble_description,
     *                    bubble_subdescription, bubble_image
     * @param mapView
     */

    Marker marker;

    public MapMarkerPopup(int layoutResId, MapView mapView) {
        super(layoutResId, mapView);
    }

    @Override
    public void onOpen(Object item) {
        marker = (Marker) item;
        TextView title = (TextView) mView.findViewById(R.id.bubble_title);
        CharSequence text = marker.getPosition().getLatitude() + " " + marker.getPosition().getLongitude();
        title.setText(text);
        ImageButton button = (ImageButton) (mView.findViewById(R.id.bubble_image));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(button.getContext(), "TODO: Add point to favourites", Toast.LENGTH_LONG).show();       // TODO: Add point to favourites list
            }
        });
    }

    @Override
    public void onClose(){
        marker.remove(getMapView());                                                                    // TODO: Fix bug with not-disappearing popup (when you click two times at the same spot)
    }
}
