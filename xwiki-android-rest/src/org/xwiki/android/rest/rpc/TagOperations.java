package org.xwiki.android.rest.rpc;

import org.xwiki.android.resources.Tag;
import org.xwiki.android.resources.Tags;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;

public interface TagOperations
{

    /**
     * Gets all the tags either from wiki or from the page. If only the URL & Wiki name fields are provided then method
     * will return wiki tags and if space name and page name is provided it will return page tags
     * 
     * @return list of all tags as a Tags object
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract Tags getTags() throws RestConnectionException, RestException;

    /**
     * Adds tag to the wiki or the page depending on the fields provided in the constructor
     * 
     * @param tag Tag object to be added
     * @return statsu of the HTTP put request
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract String addTag(Tag tag) throws RestConnectionException, RestException;

    public abstract String setTags(Tags tags) throws RestConnectionException, RestException;

}
