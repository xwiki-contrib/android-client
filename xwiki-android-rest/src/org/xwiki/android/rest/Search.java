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

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xwiki.android.resources.SearchResults;

/**
 * XWiki Android REST Search related source. Can search either in Wiki or space and provide details/list as a
 * SearchResults objects of Simple-xml object model
 */
public class Search extends HttpConnector
{
    /**
     * Path provided from XWiki RESTful API
     */
    private final String SEARCH_REQUEST_PREFIX = "/xwiki/rest/wikis/";

    /**
     * Path for perform search provided from XWiki RESful API
     */
    private final String WIKI_SEARCH_REQUEST_SUFFIX = "/search?scope=title&scope=content&scope=name&scope=object&media=xml&q=";

    /**
     * URL of the XWiki domain
     */
    private String URLprefix;

    /**
     * @param URLprefix XWiki URl ex:"www.xwiki.org"
     */
    public Search(String URLprefix)
    {
        this.URLprefix = URLprefix;
    }

    /**
     * @param wikiName name of the Wiki of space
     * @param spaceName name of the space to search
     * @param keyword searching keyword/text
     * @return list of search results as a SearchResults object
     * @throws RestConnectorException 
     */
    public SearchResults doSpacesSearch(String wikiName, String spaceName, String keyword) throws RestConnectorException
    {
        String Uri =
            "http://" + URLprefix + SEARCH_REQUEST_PREFIX + wikiName + "/spaces/" + spaceName
                + WIKI_SEARCH_REQUEST_SUFFIX + keyword;

        return buildSearchResults(super.getResponse(Uri));
    }

    /**
     * @param wikiName name of the Wik to search
     * @param keyword searching keyword/text to search
     * @return list of search results as a SearchResults object
     * @throws RestConnectorException 
     */
    public SearchResults doWikiSearch(String wikiName, String keyword) throws RestConnectorException
    {
        String Uri = "http://" + URLprefix + SEARCH_REQUEST_PREFIX + wikiName + WIKI_SEARCH_REQUEST_SUFFIX + keyword;

        return buildSearchResults(super.getResponse(Uri));
    }

    /**
     * Parse xml into a Comment object
     * 
     * @param content XML content
     * @return SearchResults object deserialized from the xml content
     */
    private SearchResults buildSearchResults(String content)
    {
        SearchResults searchresults = new SearchResults();

        Serializer serializer = new Persister();

        try {
            searchresults = serializer.read(SearchResults.class, content);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return searchresults;
    }
}
