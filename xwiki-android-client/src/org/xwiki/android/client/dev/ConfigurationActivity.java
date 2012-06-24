package org.xwiki.android.client.dev;


import org.xwiki.android.core.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class ConfigurationActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		PreferenceManager pm=getPreferenceManager();
		pm.setSharedPreferencesName("config.xml");
		addPreferencesFromResource(R.xml.config);
	}
}
