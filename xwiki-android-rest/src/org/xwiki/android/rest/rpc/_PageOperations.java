package org.xwiki.android.rest.rpc;

import org.xwiki.android.resources.Page;
import org.xwiki.android.resources.Pages;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;


class _PageOperations implements PageOperations
{
    
    /**
     * Path provided from XWiki RESTful API
     */
    private final String PAGE_URL_PREFIX = "/xwiki/rest/wikis/";

    /**
     * Path provided from XWiki RESTful API for pages
     */
    private final String PAGE_URL_SUFFIX = "/pages";

    /**
     * URL of the XWiki domain
     */
    private String URLprefix;

    /**
     * Name of Wiki for acquiring pages
     */
    private String wikiName;

    /**
     * Name of space for acquiring pages
     */
    private String spaceName;
    
    private RestClient rpc;

    /**
     * @param URLprefix  XWiki URl ex:"www.xwiki.org"
     * @param wikiName   name of the wiki in UTF-8 format
     * @param spaceName  name of the space in UTF-8 format
     * @param rpc
     */
    _PageOperations(String URLprefix, String wikiName, String spaceName, RestClient rpc)
    {

        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
        this.spaceName = spaceName;
        this.rpc=rpc;
    }

    @Override
    public Pages getAllPages() throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean exists(String pageName) throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Page getPage(String pageName) throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String addPage(Page page) throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String deletePage(String pageName) throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page getPageHistoryOnVersion(String pageName, String version) throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Pages getPageChildren(String pageName) throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page getPageTranslation(String pageName, String language) throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String addPageTranslation(Page page, String language) throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String deletePageTranslation(String pageName, String language) throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page getPageTranslation(String pageName, String language, String version) throws RestConnectionException,
        RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

}
