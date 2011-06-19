package org.xwiki.android.rest;

import org.xwiki.android.resources.SearchResults;

import com.google.gson.Gson;

public class Search extends HttpConnector
{

    private final String SEARCH_REQUEST_PREFIX = "/xwiki/rest/wikis/";

    private final String WIKI_SEARCH_REQUEST_SUFFIX = "/search?scope=name&media=json&q=";

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

        return decode(super.getResponse(Uri));
    }

    public SearchResults doWikiSearch(String wikiName, String keyword)
    {
        String Uri = "http://" + URLprefix + SEARCH_REQUEST_PREFIX + wikiName + WIKI_SEARCH_REQUEST_SUFFIX + keyword;

        return decode(super.getResponse(Uri));
    }

    // decode json content into SearchResults
    private SearchResults decode(String content)
    {
        Gson gson = new Gson();

        SearchResults searchresults = new SearchResults();
        searchresults = gson.fromJson(content, SearchResults.class);
        return searchresults;
    }
}
