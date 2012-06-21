package org.xwiki.android.client.blog;
import org.xwiki.android.client.R;
import org.xwiki.android.client.R.layout;
import org.xwiki.android.components.login.LoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Blogger extends Activity {

	private static final int REQUEST_CODE_LOGINACTIVITY = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.main);//show loading screen
		startActivity(new Intent(this,BlogPrefernces.class)); 
	}
}
