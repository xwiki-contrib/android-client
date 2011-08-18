package org.xwiki.android.test.rest;

import org.xwiki.android.resources.Class;
import org.xwiki.android.resources.Classes;
import org.xwiki.android.rest.Requests;

import android.test.AndroidTestCase;

public class ClassTest extends AndroidTestCase
{
    private String wikiName, url, username, password, classname;

    private boolean isSecured = true;

    private Requests request;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        wikiName = TestResources.WIKI_NAME;
        url = TestResources.URL;
        classname = TestResources.CLASS_NAME;

        if (isSecured) {
            username = TestResources.USERNAME;
            password = TestResources.PASSWORD;
        }

        request = new Requests(url);

        if (isSecured) {
            request.setAuthentication(username, password);
        }
    }

    public void testRequestWikiClasses() throws Throwable
    {
        Classes classes = request.requestWikiClasses(wikiName);
        assertNotNull(classes);
    }

    public void testRequestWikiClass() throws Throwable
    {
        Class clas = request.requestWikiClasses(wikiName, classname);
        assertNotNull(clas);
    }
}
