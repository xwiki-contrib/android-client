package org.xwiki.android.rest.rpc;

import org.xwiki.android.resources.Class;
import org.xwiki.android.resources.Classes;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;

public interface ClassOperations
{

    /**
     * @return list of all the Classes in the Wiki
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract Classes getWikiClasses() throws RestConnectionException, RestException;

    /**
     * @param classname name of the Class
     * @return Specific class with the provided class name
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract Class getWikiClasses(String classname) throws RestConnectionException, RestException;

}
