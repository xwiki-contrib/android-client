package org.xwiki.android.rest.ral;

import java.util.Properties;

import org.xwiki.android.resources.Space;
import org.xwiki.android.resources.Wiki;
import org.xwiki.android.rest.RestConnection;
import org.xwiki.android.rest.XWikiAPI;
import org.xwiki.android.rest.XWikiRestConnecion;
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
    
    /**
     * 
     * @param username
     * @param password
     * @param props additional properties. Like props to support OAuth when XWiki Servers support them and etc.
     * 
     */
    
    public XmlRESTFulManager(String serverUrl, String username, String password, Properties props)
    {
        this.serverUrl = serverUrl;
        this.username = username;
        this.password = password;
        throw new UnsupportedOperationException("impl in future, when need arise for additional props for connection. Like OAuth etc...");
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

    @Override
    public RestConnection getConnection()
    {
        return new XWikiRestConnecion(serverUrl,username,password);
    }

    

}
