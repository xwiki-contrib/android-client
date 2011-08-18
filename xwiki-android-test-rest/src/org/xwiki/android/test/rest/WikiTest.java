package org.xwiki.android.test.rest;

import org.xwiki.android.resources.Wikis;
import org.xwiki.android.rest.Requests;

import android.test.AndroidTestCase;

public class WikiTest extends AndroidTestCase
{
    private String url, username, password ;

    private boolean isSecured = true;

    private Requests request;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
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

    public void testRequestWiki() throws Throwable
    {
        Wikis wikis= request.requestWikis();
        assertNotNull(wikis);
    }
}
