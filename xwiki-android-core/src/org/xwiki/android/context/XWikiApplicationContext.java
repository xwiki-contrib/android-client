package org.xwiki.android.context;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.Map;

import org.xwiki.android.bgsvcs.SyncBackgroundService;
import org.xwiki.android.dal.EntityManager;
import org.xwiki.android.entity.User;
import org.xwiki.android.fileStore.FileStoreManager;
import org.xwiki.android.fileStore.FileStoreManagerImpl;
import org.xwiki.android.ral.RESTfulManager;
import org.xwiki.android.ral.XmlRESTFulManager;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author xwiki Non public class. Always program to XWikiApplicationContextAPI interface. This extends the android native
 *         Application class wich is the global singleton ApplicationContext for an application. This context is scoped
 *         to the whole application lifecycle. Stayed as a singleton throughout the application <b>if only a single
 *         process is used to run the application.</b> 
 *         Warning! : If the process is killed due to a uncaught exception all static variables will be lost as the class would be loaded again.
 *         
 *         This class can be used by calling the native method         
 *         getApplicationContext() in Activities,Services 
 *         TODO: research needed Warning! Behaviour unspecified in multi processing. (not multi threading!)
 *         Refer DalvikVM, ClassLoaders, Processes.
 */
public class XWikiApplicationContext extends Application implements XWikiApplicationContextAPI
{

    private static String tag = "XwikiAppCtx";

    // private static singleton vars.(the class gets unloaded if the app process is killed)
    private static boolean initialized = false;
    private static UserSession currSession;    
    private static XWikiApplicationContext currContext;    
    private static Map store;
    
    // private non static vars.
    ConfigSourceProvider config;

    @Override
    public void onCreate()
    {
        super.onCreate();
        // instance vars        
        
//        config = new ConfigSourceProvider(this);
//        config.putBoolean("demoBool_key", false);
//        config.putFloat("demoFloat_key", (float) 1.11);
//        config.putInt("demoInt_key", 1);
//        config.putLong("demoLong_key", 122000);
//        config.putString("demoString_key", "sas");
//        config.commit();
        
        //statics
        currContext = this; 
        if (!initialized) {
            Log.d(tag, "Context Class got loaded. Did your process get killed?");
            store=new Hashtable<String, Object>(10);
        }
        initBGServices();
    }

    @Override
    public void onTerminate()
    {
        currContext = null;
        super.onTerminate();
    }    
    
    private void initBGServices(){
    	Thread t=new Thread(){
    		@Override
    		public void run()
    		{	
    			Log.d(tag, "initializing background services");
    			Intent intent=new Intent(XWikiApplicationContext.this,SyncBackgroundService.class);
    			startService(intent);    		
    		}
    	};
    	t.start();
    }
    // state updaters
    /*
     * (non-Javadoc)
     * @see
     * org.xwiki.android.context.XWikiApplicationContextAPI#updateToAuthenticatedState(org.xwiki.android.entity.User)
     */

    public void updateToAuthenticatedState(User user)
    {
        currSession = new UserSession(user);
        initialized=true;
        Log.d(tag, "updateToAuthstate(" + user + ")");
    }

    // simple setters

    // getters

    public static XWikiApplicationContext getInstance()
    {
        return currContext;
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.context.XWikiApplicationContextAPI#getUserSession()
     */

    public UserSession getUserSession()
    {
        return currSession;
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.context.XWikiApplicationContextAPI#getConfigSourceProvider()
     */

    public ConfigSourceProvider getConfigSourceProvider()
    {
        return config;
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.context.XWikiApplicationContextAPI#getFileStoreManager()
     */

    public FileStoreManager getFileStoreManager()
    {
        FileStoreManager fsm = null;
        /**
         * activate this code when we have more than one FileStoreManager Implementations.To get impl from config file
         * Class<FileStoreManager> fsmcls; try { fsmcls =(Class<FileStoreManager>)
         * Class.forName(config.getString("fileStoreManager", "org.xwiki.android.fileStore.FileStoreManagerImpl"));
         * Constructor<FileStoreManager> fsmcon=fsmcls.getConstructor(Context.class); fsmcon.setAccessible(true);
         * fsm=fsmcon.newInstance(this); } catch (ClassNotFoundException e) { // TODO Auto-generated catch block
         * e.printStackTrace(); } catch (SecurityException e) { // TODO Auto-generated catch block e.printStackTrace();
         * } catch (NoSuchMethodException e) { // TODO Auto-generated catch block e.printStackTrace(); } catch
         * (IllegalArgumentException e) { // TODO Auto-generated catch block e.printStackTrace(); } catch
         * (InstantiationException e) { // TODO Auto-generated catch block e.printStackTrace(); } catch
         * (IllegalAccessException e) { // TODO Auto-generated catch block e.printStackTrace(); } catch
         * (InvocationTargetException e) { // TODO Auto-generated catch block e.printStackTrace(); }
         **/

        try {
            Constructor<FileStoreManagerImpl> con = FileStoreManagerImpl.class.getDeclaredConstructor(XWikiApplicationContext.class);
            con.setAccessible(true);
            fsm = con.newInstance(this);
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return fsm;// TODO: We can simply return new FileStoreManagerImpl(); this is just to show the capability of
                   // configuring it in xml.
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.context.XWikiApplicationContextAPI#getRESTfulManager()
     */

    public RESTfulManager newRESTfulManager()
    {
        return new XmlRESTFulManager();
    }

    public EntityManager newEntityManager()
    {
        return new EntityManager(this);
    }

    @Override
    public void put(String key, Object value)
    {
       store.put(key, value);        
    }

    @Override
    public Object pop(String key)
    {
       return store.remove(key);
    }

}
