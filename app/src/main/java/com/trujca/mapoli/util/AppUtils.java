package com.trujca.mapoli.util;

import static lombok.AccessLevel.PRIVATE;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;
import com.trujca.mapoli.data.auth.model.UserDetails;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class AppUtils {

    public static UserDetails toUserDetails(FirebaseUser user) {
        String displayName = user.getDisplayName();
        if (displayName == null || displayName.isEmpty()) {
            displayName = "user_" + user.getUid();
        }
        String email = user.getEmail();
        Uri photoUri = user.getPhotoUrl();
        if (photoUri != null) {
            photoUri = Uri.parse(photoUri.toString().replace("s96-c", "s300-c"));
        }
        return new UserDetails(displayName, email, photoUri);
    }
}
