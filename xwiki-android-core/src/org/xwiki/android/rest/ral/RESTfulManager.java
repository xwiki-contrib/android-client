package org.xwiki.android.rest.ral;

import org.xwiki.android.rest.XWikiRestConnector;
import org.xwiki.android.rest.rpc.RestClient;

public interface RESTfulManager
{
    DocumentRao newDocumentRao();

    SpaceRao newSpaceRao();

    WikiRao newWikiRao();

    RestClient getRestClient();
    
    @Deprecated
    XWikiRestConnector getRestConnector();

}
