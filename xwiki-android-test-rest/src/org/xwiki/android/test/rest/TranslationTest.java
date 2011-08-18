package org.xwiki.android.test.rest;

import org.xwiki.android.resources.Translations;
import org.xwiki.android.rest.Requests;

import android.test.AndroidTestCase;

public class TranslationTest extends AndroidTestCase
{
    private String wikiName, spaceName, pageName, url, username, password;

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

        if (isSecured) {
            username = TestResources.USERNAME;
            password = TestResources.PASSWORD;
        }

        request = new Requests(url);

        if (isSecured) {
            request.setAuthentication(username, password);
        }
    }

    public void testRequestAllPageTranslations() throws Throwable
    {
        Translations translations = request.requestAllPageTranslations(wikiName, spaceName, pageName);
        assertNotNull(translations);
    }
}
