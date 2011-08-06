package org.xwiki.android.rest;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xwiki.android.resources.SearchResults;


public class Search extends HttpConnector
{

    private final String SEARCH_REQUEST_PREFIX = "/xwiki/rest/wikis/";

    private final String WIKI_SEARCH_REQUEST_SUFFIX = "/search?scope=content&media=xml&q=";

    private String URLprefix;

    public Search(String URLprefix)
    {
        this.URLprefix = URLprefix;
    }

    public SearchResults doSpacesSearch(String wikiName, String spaceName, String keyword)
    {
        String Uri =
            "http://" + URLprefix + SEARCH_REQUEST_PREFIX + wikiName + "/spaces/" + spaceName
                + WIKI_SEARCH_REQUEST_SUFFIX + keyword;

        return buildSearchResults(super.getResponse(Uri));
    }

    public SearchResults doWikiSearch(String wikiName, String keyword)
    {
        String Uri = "http://" + URLprefix + SEARCH_REQUEST_PREFIX + wikiName + WIKI_SEARCH_REQUEST_SUFFIX + keyword;

        return buildSearchResults(super.getResponse(Uri));
    }

 // decode xml content to SearchResuts element
    private SearchResults buildSearchResults(String content)
    {
        SearchResults searchresults = new SearchResults();

        Serializer serializer = new Persister();

        try {
            searchresults = serializer.read(SearchResults.class, content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return searchresults;
    }
}
