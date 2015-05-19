package com.android.will.wnews.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.android.will.wnews.R;
import com.android.will.wnews.fragments.SettingsFragment;

import java.util.List;

public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        if (SettingsFragment.class.getName().equals(fragmentName)) {
            return(true);
        }
        return(false);
    }

}
