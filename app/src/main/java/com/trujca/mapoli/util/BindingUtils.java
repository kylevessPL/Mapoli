package com.trujca.mapoli.util;

import static lombok.AccessLevel.PRIVATE;

import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.databinding.BindingAdapter;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class BindingUtils {

    @BindingAdapter("android:src")
    public static void setDrawable(ImageView view, @DrawableRes int drawableRes) {
        view.setImageResource(drawableRes);
    }
}
