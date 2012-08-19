package org.xwiki.android.rest.rpc;

import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.Objects;
import org.xwiki.android.resources.Properties;
import org.xwiki.android.resources.Property;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;


class _ObjectOperations implements ObjectOperations
{
    
    /**
     * Path provided from XWiki RESTful API
     */
    private final String PAGE_URL_PREFIX = "/xwiki/rest/wikis/";

    /**
     * URL of the XWiki domain
     */
    private String URLprefix;

    /**
     * Name of Wiki for acquiring objects
     */
    private String wikiName;

    /**
     * Name of space for acquiring objects
     */
    private String spaceName;

    /**
     * Name of page for acquiring objects
     */
    private String pageName;
    private RestClient rpc;

    /**
     * @param URLprefix XWiki URl ex:"www.xwiki.org"
     * @param wikiName name of the wiki in UTF-8 format
     * @param spaceName name of the space in UTF-8 format
     * @param pageName name of the page in UTF-8 format
     * @param rpc
     */
    _ObjectOperations(String URLprefix, String wikiName, String spaceName, String pageName, RestClient rpc)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
        this.spaceName = spaceName;
        this.pageName = pageName;
        this.rpc=rpc;
    }

    @Override
    public boolean exists(String objectClassname, String objectNumber) throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Objects getAllObjects() throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String addObject(Object object) throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Objects getObjectsInClassname(String objectClassname) throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getObject(String objectClassname, String objectNumber) throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String updateObject(String objectClassname, String objectNumber, Object object)
        throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String deleteObject(String objectClassname, String objectNumber) throws RestConnectionException,
        RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Properties getObjectProperties(String objectClassname, String objectNumber) throws RestConnectionException,
        RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String addObjectProperty(String objectClassname, String objectNumber, Property property)
        throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Property getObjectProperty(String objectClassname, String objectNumber, String propertyName)
        throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

}
