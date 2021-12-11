package com.trujca.mapoli.ui.main.view;

import static java.util.Objects.requireNonNull;

import android.content.Intent;
import android.net.Uri;
import android.view.Menu;

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

    private AccountHeader header;
    private Drawer drawer;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void setup() {
        setSupportActionBar(binding.appBarMain.toolbar);
        setupNavDrawer();
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

    @NonNull
    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.appBarMain.contentMain.navHostFragmentContentMain.getId());
        return requireNonNull(navHostFragment).getNavController();
    }

    private void setupNavDrawer() {
        NavController navController = getNavController();
        requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        header = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.side_nav_bar)
                .withProfileImagesClickable(false)
                .withTextColorRes(R.color.white)
                .build();
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withToolbar(binding.appBarMain.toolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withIdentifier(1)
                                .withName(R.string.map)
                                .withIcon(R.drawable.ic_baseline_map_24)
                                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                                    navController.navigate(R.id.nav_map);
                                    drawer.closeDrawer();
                                    return true;
                                }),
                        new PrimaryDrawerItem()
                                .withIdentifier(2)
                                .withName(R.string.categories)
                                .withIcon(R.drawable.ic_baseline_local_offer_24)
                                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                                    navController.navigate(R.id.nav_categories);
                                    drawer.closeDrawer();
                                    return true;
                                }),
                        new PrimaryDrawerItem()
                                .withIdentifier(3)
                                .withName(R.string.places)
                                .withIcon(R.drawable.ic_baseline_place_24)
                                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                                    navController.navigate(R.id.nav_places);
                                    drawer.closeDrawer();
                                    return true;
                                }),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem()
                                .withIdentifier(4)
                                .withName(R.string.settings)
                                .withIcon(R.drawable.ic_baseline_settings_24)
                                .withSelectable(false)
                                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                                    drawer.closeDrawer();
                                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                                    return true;
                                })
                )
                .withAccountHeader(header)
                .build();
        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }

    private void displayCurrentUserStatus(final UserDetails userDetails) {
        header.getProfiles().clear();
        if (userDetails == null) {
            header.addProfiles(notLoggedInPlaceholder, notLoggedInProfileSettingItem);
            return;
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