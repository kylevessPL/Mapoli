package com.trujca.mapoli.ui.map.view;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.trujca.mapoli.util.Constants.LATITUDE_INITIAL;
import static com.trujca.mapoli.util.Constants.LONGTITUDE_INITIAL;
import static org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.PreferenceManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.trujca.mapoli.R;
import com.trujca.mapoli.data.favorites.model.Favorite;
import com.trujca.mapoli.data.util.RepositoryCallback;
import com.trujca.mapoli.databinding.FragmentMapBinding;
import com.trujca.mapoli.ui.base.BaseFragment;
import com.trujca.mapoli.ui.map.MapMarkerPopup;
import com.trujca.mapoli.ui.map.viewmodel.MapViewModel;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.TilesOverlay;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MapFragment extends BaseFragment<FragmentMapBinding, MapViewModel> {

    public static final String STORAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;

    private MapView map;
    private CompassOverlay compass;
    private boolean storagePermissionGranted = false;
    private boolean locationPermissionGranted = false;

    private final ActivityResultLauncher<String[]> requestPermissionsLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions ->
            permissions.forEach((permission, granted) -> {
                if (permission.equals(STORAGE_PERMISSION)) {
                    storagePermissionGranted = granted;
                }
                if (permission.equals(LOCATION_PERMISSION)) {
                    locationPermissionGranted = granted;
                }
            })
    );

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(true);
        menu.findItem(R.id.action_favourites).setVisible(true);
        menu.findItem(R.id.action_add_category).setVisible(false);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Configuration.getInstance().load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        checkPermissions();
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
        compass.enableCompass();
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
        compass.disableCompass();
    }

    @Override
    public Class<MapViewModel> getViewModelClass() {
        return MapViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_map;
    }

    @Override
    protected int getTitle() {
        return R.string.app_name;
    }

    @Override
    protected void updateUI() {
        setupMap();
    }

    private void setupMap() {
        map = binding.map;
        map.setTileSource(MAPNIK);
        map.getController().setZoom(16.0);

        // Setting zoom by pinching and map rotation
        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        RotationGestureOverlay mRotationGestureOverlay = new RotationGestureOverlay(map);
        mRotationGestureOverlay.setEnabled(true);
        map.setMultiTouchControls(true);
        map.getOverlays().add(mRotationGestureOverlay);

        // Scale bar
        ScaleBarOverlay scaleBar = new ScaleBarOverlay(map);
        scaleBar.setAlignBottom(true);
        //scaleBar.setUnitsOfMeasure();                                                                 // TODO: Change value if other units of measure selected
        map.getOverlays().add(scaleBar);

        // Compass
        compass = new CompassOverlay(getContext(), map);
        map.getOverlays().add(compass);

        // Setting start view on Lodz University of Technology
        map.getController().setCenter(new GeoPoint(LATITUDE_INITIAL, LONGTITUDE_INITIAL));
        map.setTilesScaledToDpi(true);

        // Markers created by touch
        Overlay touchOverlay = new Overlay(){
            Marker markerPresent = null;
            public boolean onSingleTapConfirmed(final MotionEvent e, final MapView mapView) {
                Projection projection = mapView.getProjection();
                GeoPoint location = (GeoPoint) projection.fromPixels((int)e.getX(), (int)e.getY());
                Marker marker = new Marker(mapView);
                marker.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_favorite_24, null));
                MapMarkerPopup popupWindow = new MapMarkerPopup(R.layout.map_pin_popup, mapView, viewModel.repository);
                marker.setInfoWindow(popupWindow);
                marker.setPosition(location);
                marker.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_map_pin, null));
                if(markerPresent != null){
                    //if(markerPresent.isInfoWindowShown())
                        markerPresent.closeInfoWindow();
                    mapView.getOverlays().remove(markerPresent);
                }
                markerPresent = marker;
                mapView.getOverlays().add(markerPresent);       // Marker will overlap other overlays and when put behind, tap function doesn't work
                mapView.invalidate();
                return true;
            }
        };
        map.getOverlays().add(touchOverlay);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.requireContext());
        boolean darkModeEnabled = sharedPreferences.getBoolean("dark_mode", false);
        if (darkModeEnabled) {
            map.getOverlayManager().getTilesOverlay().setColorFilter(TilesOverlay.INVERT_COLORS);
        }
    }

    private void checkPermissions() {
        checkStoragePermission();
        checkLocationPermission();
        if (!(storagePermissionGranted && locationPermissionGranted)) {
            if (shouldShowRequestPermissionRationale(STORAGE_PERMISSION) || shouldShowRequestPermissionRationale(LOCATION_PERMISSION)) {
                showPermissionsInfoDialog();
            }
            requestPermissions();
        }
    }

    private void requestPermissions() {
        List<String> permissions = new ArrayList<>();
        if (!storagePermissionGranted) {
            permissions.add(STORAGE_PERMISSION);
        }
        if (!locationPermissionGranted) {
            permissions.add(LOCATION_PERMISSION);
        }
        requestPermissionsLauncher.launch(permissions.toArray(new String[0]));
    }

    private void checkLocationPermission() {
        if (
                ContextCompat.checkSelfPermission(requireContext(), LOCATION_PERMISSION) == PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true;
        }
    }

    private void checkStoragePermission() {
        if (
                ContextCompat.checkSelfPermission(requireContext(), STORAGE_PERMISSION) == PERMISSION_GRANTED ||
                        Build.VERSION.SDK_INT > Build.VERSION_CODES.P
        ) {
            storagePermissionGranted = true;
        }
    }

    private void showPermissionsInfoDialog() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.permissions_required))
                .setMessage(getString(R.string.permissions_message))
                .setPositiveButton(android.R.string.ok, (dialog, i) -> dialog.dismiss())
                .create().show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_favourites) {
            favouritesPopup();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void favouritesPopup() {
        PopupMenu popup = new PopupMenu(getContext(), getActivity().findViewById(R.id.action_favourites));
        viewModel.repository.getAllFavorites(new RepositoryCallback<List<Favorite>, Void>() {
            @Override
            public void onLoading(Boolean loading) {

            }

            @Override
            public void onSuccess(List<Favorite> favorites) {
                viewModel.favorites = favorites;
                for (int i = 0; i < favorites.size(); i++){
                    popup.getMenu().add(0, i, 0, favorites.get(i).getName());
                    popup.setOnMenuItemClickListener(item -> {
                        Favorite fav = viewModel.favorites.get(item.getItemId());
                        map.getController().setCenter(new GeoPoint((double) fav.getY(), (double) fav.getX()));
                        return true;
                    });
                }
                popup.inflate(R.menu.favourites_list);
                popup.show();
            }

            @Override
            public void onError(Void unused) {
                Toast.makeText(getContext(), "REEEE", Toast.LENGTH_SHORT).show();
            }
        });
    }
}