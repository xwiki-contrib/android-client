package org.xwiki.android.entity;

import org.xwiki.android.security.Master;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author admin
 */
@DatabaseTable(tableName = "C_USER")
public class User implements Cloneable
{
    // public vars
    @DatabaseField(generatedId = true, useGetSet = true)
    private int _id;

    @DatabaseField(useGetSet = true)
    private String firstName;

    @DatabaseField(useGetSet = true)
    private String lastName;

    @DatabaseField(canBeNull = false, uniqueCombo = true, useGetSet = true)
    private String userName;

    @DatabaseField()
    private String encPassword;

    private String password;

    @DatabaseField(useGetSet = true)
    private String email;

    @DatabaseField(uniqueCombo = true, useGetSet = true)
    private String wikiRealm;
    
 // constructors
    public User()
    {
    }

    public User(String firstName, String lastName, String userName, String password, String wikiRealm, String email)
    {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.wikiRealm = wikiRealm;
    }
    
    

    public String getWikiRealm()
    {
        return wikiRealm;
    }

    public void setWikiRealm(String wikiUrl)
    {
        // TODO : pattern matching to remove unwanted path tails.
        this.wikiRealm = wikiUrl;
    }

    public int get_id()
    {
        return _id;
    }

    public void set_id(int _id)
    {
        this._id = _id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    /**
     * uses security utils to decrypt the pwd. return The decrypted pwd. null if not stored
     * 
     * @return encrypted Password
     */
    public String getPassword()
    {
        if (password == null) {
            Master m = new Master();
            if (encPassword != null) {
                password = m.decryptPassword(encPassword);
            }
        }
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password)
    {
        this.password = password;
        this.encPassword = new Master().encryptPassword(password);

    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public User clone()
    {
        User u;
        try {
            u = (User) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return u;
    }

    

}
