package com.trujca.mapoli.ui.map.view;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.trujca.mapoli.util.Constants.LATITUDE_INITIAL;
import static com.trujca.mapoli.util.Constants.LONGTITUDE_INITIAL;
import static org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.trujca.mapoli.R;
import com.trujca.mapoli.databinding.FragmentMapBinding;
import com.trujca.mapoli.ui.base.BaseFragment;
import com.trujca.mapoli.ui.map.viewmodel.MapViewModel;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MapFragment extends BaseFragment<FragmentMapBinding, MapViewModel> {

    public static final String STORAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;

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
    public void onCreate(Bundle savedInstanceState)
    {
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
        return super.onCreateView(inflater,container,savedInstanceState);
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
    public Class<MapViewModel> getViewModelClass() {
        return MapViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_map;
    }

    @Override
    protected void updateUI() {
        setupMap();
    }

    private void setupMap() {
        map = binding.map;
        map.setTileSource(MAPNIK);
        map.getController().setZoom(16.0);

        // setting start view on Lodz University of Technology
        map.getController().setCenter(new GeoPoint(LATITUDE_INITIAL, LONGTITUDE_INITIAL));
        map.setTilesScaledToDpi(true);
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
}