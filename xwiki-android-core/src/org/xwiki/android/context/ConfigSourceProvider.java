package org.xwiki.android.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.util.Log;

/**
 * Provides Application wise configuration data You need to call commit to persist the data. Uses
 * 
 * @see android.content.SharedPreferences
 * @see android.content.SharedPreferences.Editor This class instance is a singleton
 * @author sasinda
 */
// TODO: What happens when multiple threads share same pref.
public class ConfigSourceProvider implements SharedPreferences, SharedPreferences.Editor
{
    public static final String FILE_CONFIG = "config";

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    /*
     * package lvl method Use XWikiApplicationContext.getConfigSourceProvider to get an instance.
     */
    ConfigSourceProvider(Context ctx)
    {
        // init
        pref = ctx.getSharedPreferences(FILE_CONFIG, android.content.Context.MODE_WORLD_READABLE);
        editor = pref.edit();

        // copy the file assets/config.xml in applications's local file directory.(data/data/<pkg name/shared_prefs
        if (pref.getInt("version", -1) < 0) {
            try {
                InputStream is = ctx.getAssets().open("config.xml", AssetManager.ACCESS_BUFFER);
                FileOutputStream fo = ctx.openFileOutput("shared_prefs/" + FILE_CONFIG, Context.MODE_WORLD_READABLE);
                byte[] buffer = new byte[1024];
                int len = 0;
                while (len != -1) {
                    len = is.read(buffer);
                    fo.write(buffer);
                }
                fo.flush();
                fo.close();
                is.close();
            } catch (IOException e) {
                Log.e(this.getClass().getSimpleName(), "cannot copy config file exception is:\n" + e.getMessage());
                e.printStackTrace();
            }

        }

    }

    /**
     * Handy reInitialize method if the stream to the config file is lost.
     * 
     * @param appCtx
     */
    public void reInitialize(Context ctx)
    {
        // init
        pref = ctx.getSharedPreferences(FILE_CONFIG, android.content.Context.MODE_WORLD_READABLE);
        editor = pref.edit();
    }

    public Editor putString(String key, String value)
    {
        return editor.putString(key, value);
    }

    public Editor putInt(String key, int value)
    {
        return editor.putInt(key, value);
    }

    public Editor putLong(String key, long value)
    {
        return editor.putLong(key, value);

    }

    public Editor putFloat(String key, float value)
    {
        return editor.putFloat(key, value);
    }

    public Editor putBoolean(String key, boolean value)
    {
        return editor.putBoolean(key, value);
    }

    public Editor remove(String key)
    {
        return editor.remove(key);
    }

    public Editor clear()
    {
        return editor.clear();
    }

    public boolean commit()
    {
        return editor.commit();
    }

    public Map<String, ? > getAll()
    {
        return pref.getAll();
    }

    public String getString(String key, String defValue)
    {
        return pref.getString(key, defValue);
    }

    public int getInt(String key, int defValue)
    {
        return pref.getInt(key, defValue);
    }

    public long getLong(String key, long defValue)
    {
        return pref.getLong(key, defValue);
    }

    public float getFloat(String key, float defValue)
    {
        return pref.getFloat(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue)
    {
        return pref.getBoolean(key, defValue);
    }

    public boolean contains(String key)
    {
        return pref.contains(key);
    }

    /*
     * not used. The Editor's methods are implemented in this class itself.
     * @see android.content.SharedPreferences#edit()
     */

    public Editor edit()
    {
        return pref.edit();
    }

    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener)
    {
        pref.registerOnSharedPreferenceChangeListener(listener);

    }

    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener)
    {
        pref.unregisterOnSharedPreferenceChangeListener(listener);

    }

}
