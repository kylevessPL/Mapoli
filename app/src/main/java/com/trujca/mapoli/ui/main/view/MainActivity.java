package com.trujca.mapoli.ui.main.view;

import static java.util.Objects.requireNonNull;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.PopupMenu;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.trujca.mapoli.R;
import com.trujca.mapoli.databinding.ActivityMainBinding;
import com.trujca.mapoli.ui.base.BaseActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity<ActivityMainBinding>{

    private NavController navController;
    private NavigationView navView;
    private AppBarConfiguration appBarConfiguration;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

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

    public void favouritesPopup(MenuItem item) {
        View favouritesButton = findViewById(R.id.action_favourites);
        PopupMenu popup = new PopupMenu(this, favouritesButton);
        popup.getMenu().add("Weeia");                                                                   // TODO: Fill with binded data
        popup.getMenu().add("Mordor");
        popup.getMenu().add("Lodex");
        popup.getMenu().add("Akwarium");
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                item.getTitle();                                                                        // TODO: Make action depending on items details (id + name)
                return true;
            }
        });
        popup.inflate(R.menu.favourites_list);
        popup.show();
    }
}