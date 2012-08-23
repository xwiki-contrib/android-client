package org.xwiki.android.rest.ral;

import org.xwiki.android.rest.XWikiRestConnector;
import org.xwiki.android.rest.rpc.RestClient;

public interface RESTfulManager
{
    /**
     * misnormer. We will be giving the save Rao instance.
     * Use getDocumentRao() which is named correctly;
     * @return
     */
    
    DocumentRao newDocumentRao();
    DocumentRao getDocumentRao();
    
    RestClient getRestClient();
    
    @Deprecated
    XWikiRestConnector getRestConnector();

}
