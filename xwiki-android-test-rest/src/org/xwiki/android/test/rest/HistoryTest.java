package org.xwiki.android.test.rest;

import org.xwiki.android.resources.History;
import org.xwiki.android.rest.Requests;

import android.test.AndroidTestCase;

public class HistoryTest extends AndroidTestCase
{
    private String wikiName, spaceName, pageName, url, username, password, language;

    private boolean isSecured = true;

    private Requests request;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        wikiName = TestResources.WIKI_NAME;
        spaceName = TestResources.SPACE_NAME;
        pageName = TestResources.PAGE_NAME;
        url = TestResources.URL;
        language = TestResources.LANGUAGE;

        if (isSecured) {
            username = TestResources.USERNAME;
            password = TestResources.PASSWORD;
        }

        request = new Requests(url);

        if (isSecured) {
            request.setAuthentication(username, password);
        }
    }

    public void testRequestPageHistory() throws Throwable
    {

        History history = request.requestPageHistory(wikiName, spaceName, pageName);
        assertNotNull(history);
    }

    public void testRequestPageHistoryWithLanguage() throws Throwable
    {

        History history = request.requestPageHistoryInLanguage(wikiName, spaceName, pageName, language);
        assertNotNull(history);
    }
}
