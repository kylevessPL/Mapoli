package com.trujca.mapoli.ui.main.view;

import static java.util.Objects.requireNonNull;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.trujca.mapoli.R;
import com.trujca.mapoli.data.places.model.PlaceCategory;
import com.trujca.mapoli.databinding.ActivityMainBinding;
import com.trujca.mapoli.ui.base.BaseActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private NavController navController;
    private NavigationView navView;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void setupView() {
        setSupportActionBar(binding.appBarMain.toolbar);
        setupNavController();
        setupNavDrawer();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    private void setupNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.appBarMain.contentMain.navHostFragmentContentMain.getId());
        navController = requireNonNull(navHostFragment).getNavController();
    }

    private void setupNavDrawer() {
        navView = binding.navView;
        DrawerLayout drawerLayout = binding.drawerLayout;
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_map).setOpenableLayout(drawerLayout).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
}