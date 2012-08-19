package org.xwiki.android.rest.rpc;

import org.xwiki.android.resources.Page;
import org.xwiki.android.resources.Pages;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;

public interface PageOperations
{

    /**
     * @return all the pages as a Pages object in the specified space
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract Pages getAllPages() throws RestConnectionException, RestException;

    public abstract boolean exists(String pageName) throws RestConnectionException, RestException;

    /**
     * @param pageName name of the page
     * @return requested page as a Page object
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract Page getPage(String pageName) throws RestConnectionException, RestException;

    /**
     * Adds or Modify the page
     * 
     * @param page Page object to be added if not existing in the space or modified if it existing in the space
     * @return status of the HTTP put request
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract String addPage(Page page) throws RestConnectionException, RestException;

    /**
     * Deletes the page by providing name of the page
     * 
     * @param pageName name of the page
     * @return status of the HTTP delete request
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract String deletePage(String pageName) throws RestConnectionException, RestException;

    /**
     * @param pageName name of the page
     * @param version history version of the page
     * @return requested version of the page
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract Page getPageHistoryOnVersion(String pageName, String version) throws RestConnectionException,
        RestException;

    /**
     * @param pageName name of the page
     * @return children of the page as a Pages object
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract Pages getPageChildren(String pageName) throws RestConnectionException, RestException;

    /**
     * @param pageName name of the page
     * @param language language name of the page
     * @return Translated page of the provided language
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract Page getPageTranslation(String pageName, String language) throws RestConnectionException,
        RestException;

    /**
     * Adds page translation to XWiki
     * 
     * @param page Page object of the new translation
     * @param language translated language name of the page
     * @return status of the HTTP put request
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract String addPageTranslation(Page page, String language) throws RestConnectionException, RestException;

    /**
     * Deletes specific translation page
     * 
     * @param pageName name of the page
     * @param language translated language name of the page
     * @return status of the HTTP delete request
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract String deletePageTranslation(String pageName, String language) throws RestConnectionException,
        RestException;

    /**
     * @param pageName name of the page
     * @param language translated language name of the page
     * @param version version of the page
     * @return translated page of the spcific page history version
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract Page getPageTranslation(String pageName, String language, String version)
        throws RestConnectionException, RestException;

}
