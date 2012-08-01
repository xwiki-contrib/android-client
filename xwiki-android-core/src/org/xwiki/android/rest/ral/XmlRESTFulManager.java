package org.xwiki.android.rest.ral;

import org.xwiki.android.resources.Space;
import org.xwiki.android.resources.Wiki;
import org.xwiki.android.xmodel.entity.Document;

public class XmlRESTFulManager implements RESTfulManager
{
    
    String serverUrl,username,password;
    
    public XmlRESTFulManager(String serverUrl, String username, String password)
    {
        this.serverUrl = serverUrl;
        this.username = username;
        this.password = password;
    }

    public DocumentRao newDocumentRao()
    {
        return new XmlDocumentRao(serverUrl, username, password);
    }

    public SpaceRao newSpaceRao()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("not yet supported");

    }

    public WikiRao newWikiRao()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("not yet supported");

    }

}
