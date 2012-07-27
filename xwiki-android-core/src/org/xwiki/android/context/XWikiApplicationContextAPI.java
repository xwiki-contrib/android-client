package org.xwiki.android.context;

import org.xwiki.android.entity.User;
import org.xwiki.android.fileStore.FileStoreManager;
import org.xwiki.android.rest.ral.RESTfulManager;

/**
 * @author xwiki Cast the activity/service.getApplicationContext to this interface if only XWiki specific methods are
 *         needed. (Since android does not come with an android.app.Context interface(its an abstract class) we cannot
 *         extend it here.) note: If we ever need to install an AOP container we may run into problems if
 *         XWikiApplicationContext concrete class is directly used everywhere. There is possibility to
 *         XWikiApplicationContext to extend AOP Container if it extends android.app.Application.
 */

public interface XWikiApplicationContextAPI
{

    // state updaters
    void updateToAuthenticatedState(User user);

    // simple setters

    UserSession getUserSession();

    ConfigSourceProvider getConfigSourceProvider();

    FileStoreManager getFileStoreManager();

    RESTfulManager newRESTfulManager();
    /**
     * put key val pairs for passing data between activities without the overheads of serialization when passing through intents.
     * (Works only within a single VM process)
     * @param key
     * @param val
     */
    void put(String key, Object val);
    /**
     * Pop out saved data. Automatically removes the key value pair when poping it out.
     * @param key
     * @return the value for the key. (Also Deletes the key in data store)
     */
    Object pop(String key);

}
