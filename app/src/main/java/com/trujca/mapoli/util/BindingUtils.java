package com.trujca.mapoli.util;

import static lombok.AccessLevel.PRIVATE;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class BindingUtils {

    @BindingAdapter("android:src")
    public static void setDrawable(ImageView view, @DrawableRes int drawableRes) {
        view.setImageResource(drawableRes);
    }

    @BindingAdapter({"android:imageUrl", "android:placeholderDrawable"})
    public static void imageUrl(ImageView view, String imageUrl, Drawable placeholderDrawable) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .placeholder(placeholderDrawable)
                .fallback(placeholderDrawable)
                .error(placeholderDrawable)
                .centerCrop()
                .into(view);
    }
}
