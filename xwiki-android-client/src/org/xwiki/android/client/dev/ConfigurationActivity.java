package org.xwiki.android.client.dev;


import org.xwiki.android.core.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class ConfigurationActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.config);
	}
}
