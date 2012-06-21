package org.xwiki.android.client.blog;


import org.xwiki.android.client.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class BlogPrefernces extends PreferenceActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);		
		PreferenceManager pm=getPreferenceManager();
		pm.setSharedPreferencesName("preference_blog");
		addPreferencesFromResource(R.xml.preference_blog);
		
	}
}
