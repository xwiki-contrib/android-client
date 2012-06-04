package org.xwiki.android.context;

import java.util.Map;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Provides Application wise configuration data
 * You need to call commit to persist the data.
 * Uses 
 * @see android.content.SharedPreferences
 * @see android.content.SharedPreferences.Editor
 * This class instance is a singleton
 * @author sasinda
 *
 */
//TODO: Consider revisioning. Is a singleton needed. What happens when multiple threads share same pref.
public class ConfigSourceProvider  implements SharedPreferences,SharedPreferences.Editor{
	public static final String FILE_CONFIG="/data/config";
	
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	private static ConfigSourceProvider singleton;
	
	private ConfigSourceProvider(){}
	private ConfigSourceProvider(Context ctx){
		//init			
		pref=ctx.getSharedPreferences(FILE_CONFIG, android.content.Context.MODE_WORLD_READABLE);
		editor=pref.edit();	
		//clean up context obj		
	}	
	/*
	 * package lvl method
	 * Use XwikiContext.getConfigSourceProvider to get an instance.
	 */
	static ConfigSourceProvider getInstance(Context appCtx){
		if(singleton==null){
			singleton=new ConfigSourceProvider(appCtx);			
		}
		return singleton;
	}
	
	/**
	 * Handy reInitialize method if the stream to the config file is lost.
	 * @param appCtx
	 */
	public void reInitialize(Context ctx){
		//init			
		pref=ctx.getSharedPreferences("/data/config", android.content.Context.MODE_WORLD_READABLE);
		editor=pref.edit();			
	}
	@Override
	public Editor putString(String key, String value) {
		return editor.putString(key, value);		
	}
	@Override
	public Editor putInt(String key, int value) {
		return editor.putInt(key, value);		
	}
	@Override
	public Editor putLong(String key, long value) {
		return editor.putLong(key, value);
		
	}
	@Override
	public Editor putFloat(String key, float value) {
		return editor.putFloat(key, value);
	}
	@Override
	public Editor putBoolean(String key, boolean value) {
		return editor.putBoolean(key, value);
	}
	@Override
	public Editor remove(String key) {
		return editor.remove(key);
	}
	@Override
	public Editor clear() {
		return editor.clear();
	}
	@Override
	public boolean commit() {
		return editor.commit();
	}
	@Override
	public Map<String, ?> getAll() {
		return pref.getAll();		
	}
	@Override
	public String getString(String key, String defValue) {
		return pref.getString(key, defValue);		
	}
	@Override
	public int getInt(String key, int defValue) {
		return pref.getInt(key, defValue);		
	}
	@Override
	public long getLong(String key, long defValue) {
		return pref.getLong(key, defValue);		
	}
	@Override
	public float getFloat(String key, float defValue) {
		return pref.getFloat(key, defValue);		
	}
	@Override
	public boolean getBoolean(String key, boolean defValue) {
		return pref.getBoolean(key, defValue);
	}
	@Override
	public boolean contains(String key) {
		return pref.contains(key);
	}
	
	/*
	 * not used. You can get the same methods in the Config Class
	 * @see android.content.SharedPreferences#edit()
	 */
	@Override
	public Editor edit() {
		return pref.edit();
	}
	@Override
	public void registerOnSharedPreferenceChangeListener(
			OnSharedPreferenceChangeListener listener) {
		pref.registerOnSharedPreferenceChangeListener(listener);
		
	}
	@Override
	public void unregisterOnSharedPreferenceChangeListener(
			OnSharedPreferenceChangeListener listener) {
		pref.unregisterOnSharedPreferenceChangeListener(listener);
		
	}	

}
