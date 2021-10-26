package com.trujca.mapoli.ui.main.view;

import static android.content.DialogInterface.*;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Menu;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.trujca.mapoli.R;
import com.trujca.mapoli.databinding.ActivityMainBinding;

import org.osmdroid.config.Configuration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private NavController navController;
    private NavigationView navView;
    private AppBarConfiguration appBarConfiguration;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (!isGranted) {
                    showPermissionsWarningDialog();
                }
            });

    private void showPermissionsWarningDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.permissions_required))
                .setMessage(getString(R.string.permissions_message_warning))
                .setPositiveButton(android.R.string.ok, (dialog, i) -> {
                    dialog.dismiss();
                })
                .create().show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        setupNavController();
        setupNavDrawer();
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        requestMapPermissions();
    }

    private void requestMapPermissions() {
        final String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if (
                ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED ||
                shouldShowRequestPermissionRationale(permission)
        ) {
            showPermissionsInfoDialog();
        } else {
            requestPermissionLauncher.launch(permission);
        }
    }

    private void showPermissionsInfoDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.permissions_required))
                .setMessage(getString(R.string.permissions_message))
                .setPositiveButton(android.R.string.ok, (dialog, i) -> dialog.dismiss())
                .create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    private void setupNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.appBarMain.contentMain.navHostFragmentContentMain.getId());
        navController = Objects.requireNonNull(navHostFragment).getNavController();
    }

    private void setupNavDrawer() {
        navView = binding.navView;
        DrawerLayout drawerLayout = binding.drawerLayout;
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_map).setOpenableLayout(drawerLayout).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
}