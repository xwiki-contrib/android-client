package org.xwiki.android.test.rest;

import org.xwiki.android.resources.SearchResults;
import org.xwiki.android.rest.Requests;

import android.test.AndroidTestCase;

public class SearchTest extends AndroidTestCase
{
    private String wikiName, spaceName, url, username, password, keyword;

    private boolean isSecured = true;

    private Requests request;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        wikiName = TestResources.WIKI_NAME;
        spaceName = TestResources.SPACE_NAME;
        url = TestResources.URL;
        keyword = TestResources.SEARCH_KEWORD;
        
        if (isSecured) {
            username = TestResources.USERNAME;
            password = TestResources.PASSWORD;
        }

        request = new Requests(url);

        if (isSecured) {
            request.setAuthentication(username, password);
        }
    }

    public void testWikiSearch() throws Throwable
    {
        SearchResults sr= request.requestSearch(wikiName, keyword);
        assertNotNull(sr);
    }

    public void testSpaceSearch() throws Throwable
    {
        SearchResults sr= request.requestSearch(wikiName, spaceName, keyword);
        assertNotNull(sr);
    }

}
