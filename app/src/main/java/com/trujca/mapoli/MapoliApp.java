package com.trujca.mapoli;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MapoliApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //checking theme from settings and set proper mode
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean dark_mode_enabled = sharedPreferences.getBoolean("dark_mode", false);
        if (dark_mode_enabled){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }
}
