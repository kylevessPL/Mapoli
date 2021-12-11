package com.trujca.mapoli.di;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import android.content.Context;
import android.content.Intent;

import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.trujca.mapoli.R;
import com.trujca.mapoli.ui.user.view.UserActivity;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.qualifiers.ActivityContext;
import dagger.hilt.android.scopes.ActivityScoped;

@Module
@InstallIn(ActivityComponent.class)
public class ActivityModule {

    @Provides
    @ActivityScoped
    ProfileDrawerItem provideNotLoggedInProfileDrawerItem() {
        return new ProfileDrawerItem()
                .withIdentifier(0)
                .withName(R.string.app_name)
                .withEmail(R.string.not_logged_in)
                .withIcon(R.mipmap.ic_launcher)
                .withEnabled(false);
    }

    @Provides
    @ActivityScoped
    @NotLoggedInProfileSettingDrawerItem
    ProfileSettingDrawerItem provideNotLoggedInProfileSettingDrawerItem(@ActivityContext Context ctx) {
        return new ProfileSettingDrawerItem()
                .withIdentifier(-1)
                .withName(R.string.signin)
                .withIcon(R.drawable.ic_baseline_login_24)
                .withSelectable(false)
                .withOnDrawerItemClickListener((view, position, item) -> {
                    ctx.startActivity(new Intent(ctx, UserActivity.class));
                    return true;
                });
    }

    @Provides
    @ActivityScoped
    @LoggedInProfileSettingDrawerItem
    ProfileSettingDrawerItem provideLoggedInProfileSettingDrawerItem(@ActivityContext Context ctx) {
        return new ProfileSettingDrawerItem()
                .withIdentifier(-2)
                .withName(R.string.my_account)
                .withIcon(R.drawable.ic_baseline_account_circle_24)
                .withSelectable(false)
                .withOnDrawerItemClickListener((view, position, item) -> {
                    ctx.startActivity(new Intent(ctx, UserActivity.class));
                    return true;
                });
    }

    @Provides
    @ActivityScoped
    PrimaryDrawerItem provideCategoriesDrawerItem() {
        return new PrimaryDrawerItem()
                .withIdentifier(2)
                .withName(R.string.categories)
                .withIcon(R.drawable.ic_baseline_local_offer_24);
    }

    @Qualifier
    @Retention(RUNTIME)
    public @interface NotLoggedInProfileSettingDrawerItem {
    }

    @Qualifier
    @Retention(RUNTIME)
    public @interface LoggedInProfileSettingDrawerItem {
    }
}
