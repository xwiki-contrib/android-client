package org.xwiki.android.rest.rpc;

import org.xwiki.android.resources.History;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;

public interface HistoryOperations
{

    /**
     * @return version details of the page as a History object
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract History getPageHistory() throws RestConnectionException, RestException;

    /**
     * @param language translation language name of the page
     * @return page history of a page translation
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract History getPageHistory(String language) throws RestConnectionException, RestException;

}
