package org.xwiki.android.rest.rpc;

import org.xwiki.android.resources.Spaces;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;

public interface SpaceOperations
{

    /**
     * @return list of spaces in the specified Wiki
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract Spaces getSpaces() throws RestConnectionException, RestException;

}




