package org.xwiki.android.test.rest;

import org.xwiki.android.resources.Spaces;
import org.xwiki.android.rest.Requests;

import android.test.AndroidTestCase;

public class SpaceTest extends AndroidTestCase
{
    private String wikiName, url, username, password;

    private boolean isSecured = true;

    private Requests request;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        wikiName = TestResources.WIKI_NAME;
        url = TestResources.URL;

        if (isSecured) {
            username = TestResources.USERNAME;
            password = TestResources.PASSWORD;
        }

        request = new Requests(url);

        if (isSecured) {
            request.setAuthentication(username, password);
        }
    }

    public void testRequestSpace() throws Throwable
    {
        Spaces spaces = request.requestSpaces(wikiName);
        assertNotNull(spaces);
    }
}
