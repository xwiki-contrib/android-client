package org.xwiki.android.rest.rpc;

import org.xwiki.android.resources.Wikis;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;

public interface WikiOperations
{

    /**
     * @return list of all the Wikis in the provided XWiki server hosted in XWiki URL
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract Wikis getWikis() throws RestConnectionException, RestException;

}
