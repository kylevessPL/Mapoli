package com.trujca.mapoli.ui.main.view;

import static java.util.Objects.requireNonNull;
import android.view.Menu;


import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.trujca.mapoli.R;
import com.trujca.mapoli.databinding.ActivityMainBinding;

import com.trujca.mapoli.ui.base.BaseActivity;

import com.trujca.mapoli.databinding.ContentMainBinding;
import com.trujca.mapoli.ui.category.view.AddCategoryDialog;
import com.trujca.mapoli.ui.category.view.CategoryFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity implements AddCategoryDialog.NoticeDialogListener {


    private NavController navController;
    private NavigationView navView;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;


    @Override
    public void onDialogPositiveClick(DialogFragment dialog)
    {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.appBarMain.contentMain.navHostFragmentContentMain.getId());
        CategoryFragment fragment = (CategoryFragment)navHostFragment.getChildFragmentManager().getFragments().get(0); //get fragment currently displayed in navhost
        fragment.refreshData();
    }

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

}