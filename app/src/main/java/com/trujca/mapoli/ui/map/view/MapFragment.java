package com.trujca.mapoli.ui.map.view;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.widget.Toast.LENGTH_LONG;
import static com.trujca.mapoli.util.Constants.LATITUDE_CAMPUS_A;
import static com.trujca.mapoli.util.Constants.LATITUDE_CAMPUS_B;
import static com.trujca.mapoli.util.Constants.LATITUDE_CAMPUS_C;
import static com.trujca.mapoli.util.Constants.LATITUDE_INITIAL;
import static com.trujca.mapoli.util.Constants.LONGITUDE_CAMPUS_A;
import static com.trujca.mapoli.util.Constants.LONGITUDE_CAMPUS_B;
import static com.trujca.mapoli.util.Constants.LONGITUDE_CAMPUS_C;
import static com.trujca.mapoli.util.Constants.LONGITUDE_INITIAL;
import static org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK;

import android.Manifest;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.trujca.mapoli.R;
import com.trujca.mapoli.data.common.model.Coordinates;
import com.trujca.mapoli.data.map.model.LodzUniversityBuilding;
import com.trujca.mapoli.data.places.model.Place;
import com.trujca.mapoli.databinding.FragmentMapBinding;
import com.trujca.mapoli.ui.base.BaseFragment;
import com.trujca.mapoli.ui.main.viewmodel.MainViewModel;
import com.trujca.mapoli.ui.map.viewmodel.MapViewModel;
import com.trujca.mapoli.util.AppUtils;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.constants.GeoConstants;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.ScaleDiskOverlay;
import org.osmdroid.views.overlay.TilesOverlay;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MapFragment extends BaseFragment<FragmentMapBinding, MapViewModel> implements MapEventsReceiver {

    public static final String STORAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;

    private MainViewModel parentViewModel;

    private MapView map;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull final Menu menu, @NonNull final MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_map_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull final Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (parentViewModel.getCurrentUser().getValue() == null) {
            menu.removeItem(R.id.action_favourites);
        }
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
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    protected void setupView() {
        setHasOptionsMenu(true);
        setupMap();
        fetchPlaceDetails();
    }

    @Override
    protected void updateUI() {
        parentViewModel.getUniversityBuildings().observe(getViewLifecycleOwner(), this::showUniversityBuildingsOnMap);
        viewModel.getPlace().observe(getViewLifecycleOwner(), this::showPlaceOnMap);
        parentViewModel.getGeneralError().observe(getViewLifecycleOwner(), this::showGeneralErrorMessage);
        viewModel.getGeneralError().observe(getViewLifecycleOwner(), this::showGeneralErrorMessage);
    }

    private void setupMap() {
        map = binding.map;
        map.setTileSource(MAPNIK);
        map.setTilesScaledToDpi(true);
        map.getController().setZoom(16.0);
        map.getController().setCenter(new GeoPoint(LATITUDE_INITIAL, LONGITUDE_INITIAL));
        map.getOverlays().add(new RotationGestureOverlay(map));
        map.getOverlays().add(new MapEventsOverlay(this));
        map.getOverlays().add(createUniversityCampusOverlay(LATITUDE_CAMPUS_A, LONGITUDE_CAMPUS_A, 230));
        map.getOverlays().add(createUniversityCampusOverlay(LATITUDE_CAMPUS_B, LONGITUDE_CAMPUS_B, 250));
        map.getOverlays().add(createUniversityCampusOverlay(LATITUDE_CAMPUS_C, LONGITUDE_CAMPUS_C, 100));
        setMapDarkOverlay();
    }

    private void setMapDarkOverlay() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        boolean darkModeEnabled = sharedPreferences.getBoolean(getString(R.string.dark_mode_key), false);
        if (darkModeEnabled) {
            map.getOverlayManager().getTilesOverlay().setColorFilter(TilesOverlay.INVERT_COLORS);
        }
    }

    private ScaleDiskOverlay createUniversityCampusOverlay(final double latitude, final double longitude, final int radius) {
        ScaleDiskOverlay overlay = new ScaleDiskOverlay(
                requireContext(),
                new GeoPoint(latitude, longitude),
                radius,
                GeoConstants.UnitOfMeasure.meter);
        Paint paint = new Paint();
        paint.setColor(ResourcesCompat.getColor(getResources(), R.color.chateau_green_700, null));
        paint.setAlpha(40);
        overlay.setCirclePaint1(paint);
        return overlay;
    }

    private void showPlaceOnMap(final Place place) {
        Coordinates coordinates = place.getCoordinates();
        Marker marker = new PlaceMarker(map, place);
        map.getOverlays().add(marker);
        map.invalidate();
        marker.showInfoWindow();
        AppUtils.navigateToPointOnMap(map, coordinates);
    }

    private void showUniversityBuildingsOnMap(final List<LodzUniversityBuilding> universityBuildings) {
        universityBuildings.forEach(universityBuilding -> {
            Polygon polygon = new UniversityBuildingPolygon(map, universityBuilding);
            map.getOverlayManager().add(polygon);
            map.invalidate();
        });
    }

    private void showGeneralErrorMessage(final Boolean value) {
        Toast.makeText(getContext(), getString(R.string.general_error_message), LENGTH_LONG).show();
    }

    private void fetchPlaceDetails() {
        String placeId = MapFragmentArgs.fromBundle(getArguments()).getPlaceId();
        if (placeId != null) {
            viewModel.fetchPlaceDetails(placeId);
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
    public boolean singleTapConfirmedHelper(final GeoPoint point) {
        InfoWindow.closeAllInfoWindowsOn(map);
        return true;
    }

    @Override
    public boolean longPressHelper(final GeoPoint p) {
        return false;
    }
}