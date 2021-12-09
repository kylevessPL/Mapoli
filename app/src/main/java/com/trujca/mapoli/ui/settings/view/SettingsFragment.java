package com.trujca.mapoli.ui.settings.view;

import static java.util.Objects.requireNonNull;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.akexorcist.localizationactivity.core.LanguageSetting;
import com.trujca.mapoli.R;
import com.trujca.mapoli.ui.settings.viewmodel.SettingsViewModel;

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SettingsFragment extends PreferenceFragmentCompat {

    public SettingsViewModel mSettingsViewModel;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        mSettingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

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
        ((Preference) requireNonNull(findPreference("language"))).setOnPreferenceChangeListener(((preference, newValue) -> {
            System.out.println(newValue);
            Locale newLocale;
            SettingsActivity settingsActivity = (SettingsActivity) requireActivity();
            if (!newValue.toString().equals("system_default")) {
                newLocale = Locale.forLanguageTag(newValue.toString());
            } else {
                newLocale = LanguageSetting.getDefaultLanguage(requireContext());
            }
            settingsActivity.setLanguage(newLocale);

            return true;
        }));
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSettingsViewModel.currentUserLiveData.observe(getViewLifecycleOwner(), value -> ((Preference) requireNonNull(findPreference("report_a_bug"))).setVisible(value != null));
        mSettingsViewModel.mailSent.observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
                Toast.makeText(getContext(), getString(R.string.report_bug_success_toast), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
