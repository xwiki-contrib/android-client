package org.xwiki.xdroid.context;

import java.util.Hashtable;

import android.content.Context;
/**
 * 
 * @author sasinda
 *
 *Singleton class for holding application context data.
 *you can put context wise data as key value pairs using java.util.Map interface methods.
 */
public class XwikiContext extends Hashtable<String,Object> {
	
	private static XwikiContext ctx=null;//singleton
	private XwikiContext(){};//disable constructor
	
	//singleton private vars
	UserSession currSession;	
	
	public XwikiContext getInstance(){
		if(ctx==null){
			ctx=new XwikiContext();
		}
		return ctx;
	}	
	public UserSession getUserSession(){
		return currSession;
	}	
	
	public ConfigSourceProvider getConfigSourceProvider(Context appCtx){
		return ConfigSourceProvider.getInstance(appCtx);
	}

}
