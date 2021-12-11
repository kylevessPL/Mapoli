package com.trujca.mapoli.ui.map;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.trujca.mapoli.R;
import com.trujca.mapoli.data.favorites.model.Favorite;
import com.trujca.mapoli.data.favorites.repository.FavoritesRepository;
import com.trujca.mapoli.data.util.RepositoryCallback;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

import java.text.DecimalFormat;

public class MapMarkerPopup extends MarkerInfoWindow {

    /**
     * @param layoutResId layout that must contain these ids: bubble_title,bubble_description,
     *                    bubble_subdescription, bubble_image
     * @param mapView
     */

    Marker marker;
    FavoritesRepository repository;
    boolean addedToFavourites = false;

    public MapMarkerPopup(int layoutResId, MapView mapView, FavoritesRepository repository, boolean isFavourite, String name) {
        super(layoutResId, mapView);
        this.repository = repository;
        if (isFavourite){
            EditText text = (EditText) mView.findViewById(R.id.bubble_title);
            text.setText((CharSequence) name);
            text.setFocusable(false);
            ImageButton button = (ImageButton) (mView.findViewById(R.id.bubble_image));
            button.setImageResource(R.drawable.ic_favourite_button_selected);
            addedToFavourites = true;
        }
    }

    @Override
    public void onOpen(Object item) {
        marker = (Marker) item;
        TextView title = (TextView) mView.findViewById(R.id.bubble_title);
        ImageButton button = (ImageButton) (mView.findViewById(R.id.bubble_image));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!addedToFavourites){
                    if (title.getText().length() < 3){
                        Toast.makeText(button.getContext(), R.string.favourite_no_name, Toast.LENGTH_LONG).show();
                    }
                    else{
                        String id = Double.parseDouble(new DecimalFormat("#.###").format(marker.getPosition().getLatitude())) + "-" + Double.parseDouble(new DecimalFormat("#.###").format(marker.getPosition().getLongitude()));
                        Favorite fav = new Favorite(id, title.getText().toString(), (float)marker.getPosition().getLongitude(), (float)marker.getPosition().getLatitude());        // TODO: deal with this sheeit
                        repository.addFavorite(fav, new RepositoryCallback<Void, Void>() {
                                    @Override
                                    public void onLoading(Boolean loading) {

                                    }

                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(button.getContext(), R.string.favourite_added, Toast.LENGTH_LONG).show();
                                        title.setFocusable(false);
                                        button.setImageResource(R.drawable.ic_favourite_button_selected);
                                        addedToFavourites = true;
                                    }

                                    @Override
                                    public void onError(Void unused) {
                                        Toast.makeText(button.getContext(), R.string.favourite_not_added, Toast.LENGTH_LONG).show();
                                    }
                                }
                        );

                    }
                }
                else {
                    // TODO: Implement favourites deletion
                }
            }
        });
    }

    @Override
    public void onClose(){
        marker.remove(getMapView());                                                                    // TODO: Fix bug with not-disappearing popup (when you click two times at the same spot)
    }
}
