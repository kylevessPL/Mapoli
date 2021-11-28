package com.trujca.mapoli;

import static com.mikepenz.materialdrawer.util.DrawerImageLoader.Tags.PROFILE;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

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
}
