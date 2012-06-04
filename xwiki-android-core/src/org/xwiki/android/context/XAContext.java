package org.xwiki.android.context;

import java.util.Hashtable;

import android.content.Context;
/**
 * 
 * @author sasinda
 *
 *Singleton class for holding application context data.
 *you can put context wise data as key value pairs using java.util.Map interface methods.
 */
public class XAContext extends Hashtable<String,Object> {
	
	private static XAContext ctx=null;//singleton
	private XAContext(){};//disable constructor
	
	//singleton private vars
	UserSession currSession;	
	
	public static XAContext getInstance(){
		if(ctx==null){
			ctx=new XAContext();
			ctx.currSession=new UserSession();
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
