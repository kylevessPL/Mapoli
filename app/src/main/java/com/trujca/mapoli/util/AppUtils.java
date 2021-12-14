package com.trujca.mapoli.util;

import static lombok.AccessLevel.PRIVATE;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;
import com.trujca.mapoli.data.auth.model.UserDetails;
import com.trujca.mapoli.data.common.model.Coordinates;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

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

    public static void navigateToPointOnMap(final MapView map, final Coordinates coordinates) {
        GeoPoint point = new GeoPoint(coordinates.getLatitude(), coordinates.getLongitude());
        map.getController().animateTo(point);
    }
}
