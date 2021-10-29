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
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.trujca.mapoli.R;
import com.trujca.mapoli.databinding.FragmentMapBinding;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {

    public static final String STORAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;

    private FragmentMapBinding binding;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        Configuration.getInstance().load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()));
        map = binding.map;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateUI();
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateUI() {
        mapSetup();
    }

    private void mapSetup() {
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