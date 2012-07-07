package org.xwiki.android.ral.reference;

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
        XWikiApplicationContextAPI ctx = XWikiApplicationContext.getInstance();
        String server = ctx.getUserSession().getRealm(); // host+port //TODO: When xwiki-android supports multiple
                                                         // wikis, which may be seperate authentication realms, you
                                                         // might need to change here.
        String arg[] = server.split(":");
        host = arg[0];
        port = arg[1];
    }

    // special getters
    public String getAuthority()
    {
        return authInfo.getUserName() + ":" + authInfo.getPassword() + "@" + host + ":" + port;
    }

    public abstract String getURL();

    // special setters

    public void setFromURL() throws MalformedURLException
    {
        // TODO: implement
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
