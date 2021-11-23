package com.trujca.mapoli.data.auth.model;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lombok.Value;

@Value
public class UserDetails {
    @NonNull
    String displayName;
    @NonNull
    String email;
    @Nullable
    Uri avatarUri;
}
