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
import org.xwiki.android.resources.History;

/**
 * XWiki Android REST History related source. Can get the History details/list as a History objects of Simple-xml object
 * model
 */
public class HistoryResources extends HttpConnector
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
     * Name of Wiki for acquiring history
     */
    private String wikiName;

    /**
     * Name of space for acquiring history
     */
    private String spaceName;

    /**
     * Name of page for acquiring history
     */
    private String pageName;

    /**
     * @param URLprefix XWiki URl ex:"www.xwiki.org"
     * @param wikiName name of the wiki in UTF-8 format
     * @param spaceName name of the space in UTF-8 format
     * @param pageName name of the page in UTF-8 format
     */
    public HistoryResources(String URLprefix, String wikiName, String spaceName, String pageName)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
        this.spaceName = spaceName;
        this.pageName = pageName;
    }

    /**
     * @return version details of the page as a History object
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public History getPageHistory() throws RestConnectionException, RestException
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/history";

        return buildHistory(super.getResponse(Uri));
    }

    /**
     * @param language translation language name of the page
     * @return page history of a page translation
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public History getPageHistory(String language) throws RestConnectionException, RestException
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/translations/" + language + "/history";

        return buildHistory(super.getResponse(Uri));
    }

    /**
     * Parse xml into a History object
     * 
     * @param content XML content
     * @return History object deserialized from the xml content
     */
    private History buildHistory(String content)
    {
        History history = new History();

        Serializer serializer = new Persister();

        try {
            history = serializer.read(History.class, content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return history;
    }

    /**
     * Generate XML content from the History object
     * 
     * @param history History Object to be serialized into XML
     * @return XML content of the provided History object
     */
    private String buildXmlHistory(History history)
    {
        Serializer serializer = new Persister();

        StringWriter result = new StringWriter();

        try {
            serializer.write(history, result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result.toString();
    }

}
