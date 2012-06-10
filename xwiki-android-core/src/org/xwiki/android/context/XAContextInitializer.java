package org.xwiki.android.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.xwiki.android.entity.User;

import org.xwiki.android.core.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class XAContextInitializer {

	public static void updateToAuthenticatedState(User u){
		XAContext ctx=XAContext.getInstance();
		ctx.getUserSession().initilize(u);
	}
	
	public static void initialize(Context appCtx){
		
		File f=new File("shared_prefs/config.xml");
		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
			if(fis.available()==0){//this file has 0 bytes of data.
				PreferenceManager.setDefaultValues(appCtx, GlobalConstants.FILE_CONFIG,Context.MODE_PRIVATE, R.xml.config, false);
			}
		} catch (FileNotFoundException e) {//file not created yet.
			Log.d(XAContextInitializer.class.getSimpleName(),"created config file");
			PreferenceManager.setDefaultValues(appCtx, GlobalConstants.FILE_CONFIG,Context.MODE_PRIVATE, R.xml.config, true);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d(XAContextInitializer.class.getSimpleName(), "initializing completed");
		
		
	}
}
