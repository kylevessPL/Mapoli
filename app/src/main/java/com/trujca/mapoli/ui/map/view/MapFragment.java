package com.trujca.mapoli.ui.map.view;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
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

public class MapFragment extends Fragment {

    private static final String requestedPermission = Manifest.permission.READ_EXTERNAL_STORAGE;
    private FragmentMapBinding binding;
    private MapView map;
    private boolean permissionGranted = false;

    private final ActivityResultLauncher<String> requestPermissionsLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            permissionGranted = true;
        }
    });

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        Configuration.getInstance().load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()));
        map = binding.map;
        //setting start view on Lodz University of Technology
        map.getController().setZoom(16.0);
        map.getController().setCenter(new GeoPoint(51.750790, 19.453));
        map.setTilesScaledToDpi(true);
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
        map.setTileSource(MAPNIK);
    }

    private void checkPermissions() {
        if (
                ContextCompat.checkSelfPermission(requireContext(), requestedPermission) == PERMISSION_GRANTED ||
                        Build.VERSION.SDK_INT > Build.VERSION_CODES.P
        ) {
            permissionGranted = true;
        }
        if (!permissionGranted) {
            if (shouldShowRequestPermissionRationale(requestedPermission)) {
                showPermissionsInfoDialog();
            }
            requestPermissionsLauncher.launch(requestedPermission);
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