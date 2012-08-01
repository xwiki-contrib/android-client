/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.xwiki.android.rest;

import java.io.StringWriter;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xwiki.android.resources.Page;
import org.xwiki.android.resources.Pages;

/**
 * XWiki Android REST Page(s) related source. Can get the Page details/list as a Page(s) objects of Simple-xml object
 * model
 */
public class PageResources extends HttpConnector
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

    /**
     * @param URLprefix XWiki URl ex:"www.xwiki.org"
     * @param wikiName name of the wiki in UTF-8 format
     * @param spaceName name of the space in UTF-8 format
     */
    public PageResources(String URLprefix, String wikiName, String spaceName)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
        this.spaceName = spaceName;
    }
    
    

    /**
     * @return all the pages as a Pages object in the specified space
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public Pages getAllPages() throws RestConnectionException, RestException
    {
        String Uri = "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + PAGE_URL_SUFFIX;

        return buildPages(super.getResponse(Uri));
    }
    
    
    public boolean exists(String pageName) throws RestConnectionException,RestException{
        String Uri = "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName;
        int code=headRequest(Uri);
        
        if(code==200){
            return true;
        }if(code==404){
            return false;
        }else{
            throw new RestException(code);
        }        
       
    }

    /**
     * @param pageName name of the page
     * @return requested page as a Page object
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public Page getPage(String pageName) throws RestConnectionException, RestException
    {
        String Uri = "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName;

        return buildPage(super.getResponse(Uri));
    }

    /**
     * Adds or Modify the page
     * 
     * @param page Page object to be added if not existing in the space or modified if it existing in the space
     * @return status of the HTTP put request
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public String addPage(Page page) throws RestConnectionException, RestException
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + page.getName();

        return super.putRequest(Uri, buildXmlPage(page));
    }

    /**
     * Deletes the page by providing name of the page
     * 
     * @param pageName name of the page
     * @return status of the HTTP delete request
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public String deletePage(String pageName) throws RestConnectionException, RestException
    {
        String Uri = "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName;

        if (super.getIsSecured()) {
            return super.deleteRequest(Uri);
        } else {
            return "No authenticaiton details found";
        }

    }

    /**
     * @param pageName name of the page
     * @param version history version of the page
     * @return requested version of the page
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public Page getPageHistoryOnVersion(String pageName, String version) throws RestConnectionException, RestException
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/history/" + version;

        return buildPage(super.getResponse(Uri));
    }

    /**
     * @param pageName name of the page
     * @return children of the page as a Pages object
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public Pages getPageChildren(String pageName) throws RestConnectionException, RestException
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/children";

        return buildPages(super.getResponse(Uri));
    }

    /**
     * @param pageName name of the page
     * @param language language name of the page
     * @return Translated page of the provided language
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public Page getPageTranslation(String pageName, String language) throws RestConnectionException, RestException
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/translations/" + language;

        return buildPage(super.getResponse(Uri));
    }

    /**
     * Adds page translation to XWiki
     * 
     * @param page Page object of the new translation
     * @param language translated language name of the page
     * @return status of the HTTP put request
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public String addPageTranslation(Page page, String language) throws RestConnectionException, RestException
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + page
                + "/translations/" + language;

        String content = page.toString();

        return super.putRequest(Uri, content);
    }

    /**
     * Deletes specific translation page
     * 
     * @param pageName name of the page
     * @param language translated language name of the page
     * @return status of the HTTP delete request
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public String deletePageTranslation(String pageName, String language) throws RestConnectionException, RestException
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/translations/" + language;

        return super.deleteRequest(Uri);
    }

    /**
     * @param pageName name of the page
     * @param language translated language name of the page
     * @param version version of the page
     * @return translated page of the spcific page history version
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public Page getPageTranslation(String pageName, String language, String version) throws RestConnectionException, RestException
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/translations/" + language + "/history/" + version;

        return buildPage(super.getResponse(Uri));
    }

    /**
     * Parse xml into a Pages object
     * 
     * @param content XML content
     * @return Pages object deserialized from the xml content
     */
    private Pages buildPages(String content)
    {
        Pages pages = new Pages();

        Serializer serializer = new Persister();

        try {
            pages = serializer.read(Pages.class, content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return pages;
    }

    /**
     * Generate XML content from the Pages object
     * 
     * @param pages Pages object to be serialized into XML
     * @return XML content of the provided Pages object
     */
    private String buildXmlPages(Pages pages)
    {
        Serializer serializer = new Persister();

        StringWriter result = new StringWriter();

        try {
            serializer.write(pages, result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result.toString();
    }

    /**
     * Parse xml into a Page object
     * 
     * @param content XML content
     * @return Page object deserialized from the xml content
     */
    private Page buildPage(String content)
    {
        Page page = new Page();

        Serializer serializer = new Persister();

        try {
            page = serializer.read(Page.class, content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return page;
    }

    /**
     * Generate XML content from the Page object
     * 
     * @param page Page object to be serialized into XML
     * @return XML content of the provided Page object
     */
    private String buildXmlPage(Page page)
    {
        Serializer serializer = new Persister();

        StringWriter result = new StringWriter();

        try {
            serializer.write(page, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}
