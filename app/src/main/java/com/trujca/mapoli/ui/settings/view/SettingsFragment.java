package com.trujca.mapoli.ui.settings.view;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.trujca.mapoli.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }

    @Override
    public void onStart() {
        super.onStart();
        findPreference("about").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AboutDialog aboutDialog = new AboutDialog();
                aboutDialog.show(getParentFragmentManager(), AboutDialog.TAG);

                return true;
            }
        });
    }
}
