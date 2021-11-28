package com.trujca.mapoli.ui.settings.view;

import static java.util.Objects.requireNonNull;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.trujca.mapoli.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        SwitchPreferenceCompat darkModePreference = findPreference(getString(R.string.dark_mode_key));
        if (darkModePreference != null) {
            darkModePreference.setOnPreferenceChangeListener(((preference, newValue) -> {
                if ((boolean) newValue) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                return true;
            }));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        ((Preference) requireNonNull(findPreference("about"))).setOnPreferenceClickListener(preference -> {
            AboutDialog aboutDialog = new AboutDialog();
            aboutDialog.show(getParentFragmentManager(), AboutDialog.TAG);
            return true;
        });
        ((Preference) requireNonNull(findPreference("report_a_bug"))).setOnPreferenceClickListener(preference -> {
            ReportBugDialog reportBugDialog = new ReportBugDialog();
            reportBugDialog.show(getParentFragmentManager(), ReportBugDialog.TAG);
            return true;
        });
    }
}
