package com.trujca.mapoli.ui.main.view;

import static java.util.Objects.requireNonNull;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.trujca.mapoli.R;
import com.trujca.mapoli.data.auth.model.UserDetails;
import com.trujca.mapoli.databinding.ActivityMainBinding;
import com.trujca.mapoli.di.ActivityModule.LoggedInProfileSettingDrawerItem;
import com.trujca.mapoli.di.ActivityModule.NotLoggedInProfileSettingDrawerItem;
import com.trujca.mapoli.ui.base.BaseActivity;
import com.trujca.mapoli.ui.main.viewmodel.MainViewModel;
import com.trujca.mapoli.ui.settings.view.SettingsActivity;

import javax.inject.Inject;

import agency.tango.android.avatarview.AvatarPlaceholder;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    @Inject
    protected ProfileDrawerItem notLoggedInPlaceholder;
    @Inject
    @NotLoggedInProfileSettingDrawerItem
    protected ProfileSettingDrawerItem notLoggedInProfileSettingItem;
    @Inject
    @LoggedInProfileSettingDrawerItem
    protected ProfileSettingDrawerItem loggedInProfileSettingItem;
    @Inject
    protected PrimaryDrawerItem categoriesDrawerItem;

    private Drawer drawer;
    private AccountHeader header;

    @Override
    protected void setup() {
        setSupportActionBar(binding.toolbar);
        setupNavDrawer();
        setupNavController();
    }

    @Override
    protected void updateUI() {
        viewModel.getCurrentUser().observe(this, this::displayCurrentUserStatus);
    }

    @Override
    public Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    private void setupNavController() {
        NavController navController = getNavController();
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.nav_places_category) {
                drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
                requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
                return;
            }
            requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
            drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        });
    }

    @NonNull
    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.contentMain.navHostMain.getId());
        return requireNonNull(navHostFragment).getNavController();
    }

    private void setupNavDrawer() {
        NavController navController = getNavController();
        header = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.side_nav_bar)
                .withProfileImagesClickable(false)
                .withTextColorRes(R.color.white)
                .build();
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withToolbar(binding.toolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withIdentifier(1)
                                .withName(R.string.map)
                                .withIcon(R.drawable.ic_map_24),
                        new PrimaryDrawerItem()
                                .withIdentifier(3)
                                .withName(R.string.places)
                                .withIcon(R.drawable.ic_place_48),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem()
                                .withIdentifier(4)
                                .withName(R.string.settings)
                                .withIcon(R.drawable.ic_settings_24)
                                .withSelectable(false)
                )
                .withOnDrawerNavigationListener(view -> {
                    onBackPressed();
                    return true;
                })
                .withOnDrawerItemClickListener((view, position, item) -> {
                    drawer.closeDrawer();
                    switch ((int) item.getIdentifier()) {
                        case 1:
                            navController.navigate(R.id.nav_map);
                            break;
                        case 2:
                            navController.navigate(R.id.nav_categories);
                            break;
                        case 3:
                            navController.navigate(R.id.nav_places);
                            break;
                        case 4:
                            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                            break;
                        default:
                            return false;
                    }
                    return true;
                })
                .withAccountHeader(header)
                .build();
    }

    private void displayCurrentUserStatus(final UserDetails userDetails) {
        invalidateOptionsMenu();
        boolean loggedIn = userDetails != null;
        header.getProfiles().clear();
        if (!loggedIn) {
            drawer.removeItem(2);
            header.addProfiles(notLoggedInPlaceholder, notLoggedInProfileSettingItem);
            return;
        }
        viewModel.fetchFavorites();
        if (drawer.getDrawerItem(2) == null) {
            drawer.addItemAtPosition(categoriesDrawerItem, 2);
        }
        ProfileDrawerItem profileItem = createProfileDrawerItem(userDetails);
        header.addProfiles(profileItem, loggedInProfileSettingItem);
    }

    @NonNull
    private ProfileDrawerItem createProfileDrawerItem(final UserDetails userDetails) {
        String displayName = userDetails.getDisplayName();
        String email = userDetails.getEmail();
        Uri avatarUri = userDetails.getAvatarUri();
        ProfileDrawerItem profileItem = new ProfileDrawerItem()
                .withIdentifier(0)
                .withName(displayName)
                .withEmail(email)
                .withEnabled(false);
        if (avatarUri != null) {
            profileItem.withIcon(avatarUri);
        } else {
            AvatarPlaceholder avatar = new AvatarPlaceholder(displayName);
            profileItem.withIcon(avatar);
        }
        return profileItem;
    }
}