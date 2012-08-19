package org.xwiki.android.rest.rpc;

import org.xwiki.android.resources.SearchResults;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;

public interface SearchOperations
{

    /**
     * @param wikiName name of the Wiki of space
     * @param spaceName name of the space to search
     * @param keyword searching keyword/text
     * @return list of search results as a SearchResults object
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract SearchResults doSpacesSearch(String wikiName, String spaceName, String keyword)
        throws RestConnectionException, RestException;

    /**
     * @param wikiName name of the Wik to search
     * @param keyword searching keyword/text to search
     * @return list of search results as a SearchResults object
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract SearchResults doWikiSearch(String wikiName, String keyword) throws RestConnectionException,
        RestException;

}
