package org.xwiki.android.rest.reference;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;

import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.context.XWikiApplicationContextAPI;
import org.xwiki.android.entity.User;

import com.j256.ormlite.field.DatabaseField;

/**
 * @author xwiki gsoc 2012 support both file:// (for local) and http:// (for remote) URI types.
 */
public abstract class EntityReference<T> implements Serializable
{
    // do not mark this class as @DatabaseTable, table inheritance is not yet supported in ORMLite.
    // make inheriting class mark @DatabaseTable

    
    // !***! These are to identify multiple Xwiki servers that may have identically named but different documents.
    
    /**
     * http:// scheme. host section sample:- http://username:password@host:8080/directory/file?querryparam #fragment
     */
    @DatabaseField
    protected String host;

    @DatabaseField
    protected String port;

    @DatabaseField
    protected String serverName; // also named urlPrefix in old code.

    /**
     * http:// scheme. user info section: the username:password part
     */
    @DatabaseField(foreign = true)
    User authInfo;

    public EntityReference()
    {
               
    }

    // special getters
    public String getAuthority()
    {
        return authInfo.getUserName() + ":" + authInfo.getPassword() + "@" + host + ":" + port;
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public String getPort()
    {
        return port;
    }

    public void setPort(String port)
    {
        this.port = port;
    }

    /**
     * @return host+port , www.xwiki.org
     */
    public String getServerName()
    {
        return serverName;
    }

    /**
     * @param address : host:port or something like www.xwiki.org.
     */
    public void setServerName(String address)
    {
        if (address.contains(":")) {
            String[] args = address.split(":");
            host = args[0];
            port = args[1];
        }
        serverName = address;
    }

    public User getAuthInfo()
    {
        return authInfo;
    }

    public void setAuthInfo(User authInfo)
    {
        this.authInfo = authInfo;
    }

}
