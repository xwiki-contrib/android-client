package org.xwiki.android.context;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

import org.xwiki.android.dal.EntityManager;
import org.xwiki.android.entity.User;
import org.xwiki.android.fileStore.FileStoreManager;
import org.xwiki.android.fileStore.FileStoreManagerImpl;
import org.xwiki.android.ral.RESTfulManager;

import android.app.Application;
import android.content.Context;
/**
 * 
 * @author xwiki
 * 
 * Non public class. Always program to XWikiApplicationContext interface.
 *
 *This extends the android native Application class wich is the global singleton ApplicationContext for an application.
 *This context is scoped to the whole application lifecycle. 
 *Stayed as a singleton throughout the application <b>if only a single process is used to run the application.</b>
 *This class can be used by calling the native method getApplicationContext() in Activities,Services
 *
 *TODO: research needed
 *Warning!
 *If this class ever needs extentions
 *Do not save state that needs to  continue for whole application instance as instance vars. The application instance may also be killed in extreme
 *cases so the saved state will be lost.
 *Explanation:
 *Application instance will run in a single process. activity.getApplicationContext may return a different ApplicationContext object if
 *a component runs from another process.
 *However multiple processes will use the same class loader and the static variables
 *will always be shared.
 *
 *To tell it simply Android promises to keep the Application object singleton for only the current process.If the process is killed
 *(i.e application is killed ) and restarted when user needs it again a new Application Context will be created.
 *Its safe to use static vars.
 *
 *Also do not apply a singleton pattern like follows
 *keep a static instance of the XWikiContext to save data. 
 *in setter methods do not use,
 *this.var="some val" 
 *use 
 *singleton.var="some val"
 *THIS IS BAD.(Because ApplicationContext is somewhat heavy, and not be garbage collected if a static var refers to it)
 *
 *Refer DalvikVM, ClassLoaders, Processes. 
 *TODO: research needed
 *
 */
public class XWikiApplicationContext extends Application implements XWikiApplicationContextInterface{	
	
	
	//private static singleton vars	
	private static boolean initialized=false;
	private static UserSession currSession;
	
	//private static non singleton vars.
	private static XWikiApplicationContext currContext;
	
	//private non static vars.
	ConfigSourceProvider config;
	
	@Override
	public void onCreate() {
		super.onCreate();
		//instance vars
		config=new ConfigSourceProvider(this);
		config.putBoolean("demoBool_key", false);
		config.putFloat("demoFloat_key", (float)1.11);
		config.putInt("demoInt_key",1);
		config.putLong("demoLong_key", 122000);
		config.putString("demoString_key", "sas");
		config.commit();
		//non singleton statics
		currContext=this;
		//singleton vars
		if(!initialized){			
			
		}		
	}
	@Override
	public void onTerminate() {
		currContext=null;
		super.onTerminate();
	}
	//state updaters
	/* (non-Javadoc)
	 * @see org.xwiki.android.context.XWikiApplicationContext2#updateToAuthenticatedState(org.xwiki.android.entity.User)
	 */
	@Override
	public void updateToAuthenticatedState(User user){
		currSession=new UserSession(user);		
	}
	//simple setters	
	
	//getters	
	
	public static XWikiApplicationContext getInstance(){
		return currContext;
	}	
	
	/* (non-Javadoc)
	 * @see org.xwiki.android.context.XWikiApplicationContext2#getUserSession()
	 */
	@Override
	public UserSession getUserSession(){
		return currSession;		
	}	
	
	/* (non-Javadoc)
	 * @see org.xwiki.android.context.XWikiApplicationContext2#getConfigSourceProvider()
	 */
	@Override
	public ConfigSourceProvider getConfigSourceProvider(){
		return config;
	}
	
	/* (non-Javadoc)
	 * @see org.xwiki.android.context.XWikiApplicationContext2#getFileStoreManager()
	 */
	@Override
	public FileStoreManager newFileStoreManager(){
		FileStoreManager fsm=null;
		/**   activate this code when we have more than one FileStoreManager Implementations.To get impl from config file
		Class<FileStoreManager> fsmcls;
		try {
			fsmcls =(Class<FileStoreManager>) Class.forName(config.getString("fileStoreManager", "org.xwiki.android.fileStore.FileStoreManagerImpl"));
			Constructor<FileStoreManager> fsmcon=fsmcls.getConstructor(Context.class);
			fsmcon.setAccessible(true);
			fsm=fsmcon.newInstance(this);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		**/
		
		try {
			Constructor<FileStoreManagerImpl> con=FileStoreManagerImpl.class.getDeclaredConstructor(Context.class);
			con.setAccessible(true);
			fsm=con.newInstance(this);
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
		return fsm;
	}
	
	/* (non-Javadoc)
	 * @see org.xwiki.android.context.XWikiApplicationContext2#getRESTfulManager()
	 */
	@Override
	public RESTfulManager newRESTfulManager(){
		return null;
	}
	
	public EntityManager newEntityManager(){
		return new EntityManager(this);
	}
	
}
