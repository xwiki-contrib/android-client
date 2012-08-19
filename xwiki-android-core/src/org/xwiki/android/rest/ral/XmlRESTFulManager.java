package org.xwiki.android.rest.ral;

import java.util.Properties;

import org.xwiki.android.rest.XWikiRestConnector;
import org.xwiki.android.rest.rpc.RestClient;

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
    public RestClient getRestClient()
    {
       throw new UnsupportedOperationException("under construction. see rest.rpc package in rest module");
    }

    @Override
    public XWikiRestConnector getRestConnector()
    {        
        return new XWikiRestConnector(serverUrl, username, password);
    }

   

    

}
