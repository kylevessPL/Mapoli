package com.trujca.mapoli.util;

import androidx.annotation.NonNull;

import agency.tango.android.avatarview.AvatarPlaceholder;
import agency.tango.android.avatarview.ImageLoaderBase;
import agency.tango.android.avatarview.views.AvatarView;

public class GlideLoader extends ImageLoaderBase {

    public GlideLoader() {
        super();
    }

    public GlideLoader(String defaultPlaceholderString) {
        super(defaultPlaceholderString);
    }

    @Override
    public void loadImage(@NonNull AvatarView avatarView, @NonNull AvatarPlaceholder avatarPlaceholder, String avatarUrl) {
        GlideApp.with(avatarView.getContext())
                .load(avatarUrl)
                .placeholder(avatarPlaceholder)
                .fallback(avatarPlaceholder)
                .error(avatarPlaceholder)
                .centerCrop()
                .into(avatarView);
    }
}
