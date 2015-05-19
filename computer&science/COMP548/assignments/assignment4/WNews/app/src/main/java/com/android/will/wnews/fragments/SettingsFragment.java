package com.android.will.wnews.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceFragment;

import com.android.will.wnews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }

}
