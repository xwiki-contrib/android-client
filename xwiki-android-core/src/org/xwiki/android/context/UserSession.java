package org.xwiki.android.context;

import java.util.Hashtable;

import org.xwiki.android.entity.User;

public class UserSession extends Hashtable
{

    private String userName;

    private String password;

    private String realm;

    private User user;

    // constructing a new UserSession() prohibited outside package.
    UserSession(User u)
    {
        user = u;
        // TODO:have logic to decrypt when encryption is enabled.
        password = u.getPassword();
        userName = u.getUserName();
        realm = u.getWikiRealm();
    }

    public String getUserName()
    {
        return userName;
    }

    /**
     * Read pwd from persistant store. Decrypt it and return. Does not store plain text pwd in memory for enhanced
     * security.
     * 
     * @return password of current user plain text.
     */
    public String getPassword()
    {
        // TODO: read pwd from a uri and return decrypted pwd.
        return password;
    }

    public User getUser()
    {
        return user;
    }

    /**
     * @return the realm for which the user is authenticated for. ex: dev.xwiki.org/
     */
    public String getRealm()
    {
        return realm;
    }

}
