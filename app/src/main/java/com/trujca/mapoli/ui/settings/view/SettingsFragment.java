package com.trujca.mapoli.ui.settings.view;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.trujca.mapoli.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}
