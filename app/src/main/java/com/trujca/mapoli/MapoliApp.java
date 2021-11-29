package com.trujca.mapoli;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;
import static com.mikepenz.materialdrawer.util.DrawerImageLoader.Tags.PROFILE;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.bumptech.glide.Glide;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MapoliApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initDrawer();
        setTheme();
    }

    private void initDrawer() {
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {

            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder, String tag) {
                Glide.with(imageView.getContext())
                        .load(uri)
                        .placeholder(placeholder)
                        .fallback(placeholder)
                        .error(placeholder)
                        .centerCrop()
                        .into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Glide.with(imageView.getContext()).clear(imageView);
            }

            @Override
            public Drawable placeholder(Context ctx, String tag) {
                if (PROFILE.name().equals(tag)) {
                    return DrawerUIUtils.getPlaceHolder(ctx);
                }
                return super.placeholder(ctx, tag);
            }
        });
    }

    private void setTheme() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean darkModeEnabled = sharedPreferences.getBoolean(getString(R.string.dark_mode_key), false);
        if (darkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);
        }
    }
}
