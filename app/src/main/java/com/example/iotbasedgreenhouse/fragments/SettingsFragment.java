package com.example.iotbasedgreenhouse.fragments;

import android.os.Bundle;

import com.example.iotbasedgreenhouse.R;

import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_pref, rootKey);
    }

}