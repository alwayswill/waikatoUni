package org.stevej.android.propertyfinder.activities;

import java.util.List;

import org.stevej.android.propertyfinder.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class MultiScreenSettingsActivity extends PreferenceActivity  {
	private static final String	TAG	= "MultiScreenSettingsActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onBuildHeaders(List<Header> target) {
		loadHeadersFromResource(R.xml.preference_headers, target);
	}
}
