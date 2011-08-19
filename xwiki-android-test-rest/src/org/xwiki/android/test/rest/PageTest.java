package org.xwiki.android.test.rest;

import org.xwiki.android.resources.Page;
import org.xwiki.android.resources.Pages;
import org.xwiki.android.rest.Requests;

import android.test.AndroidTestCase;

public class PageTest extends AndroidTestCase
{
    private String wikiName, spaceName, pageName, url, username, password, language, version;

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
        version = TestResources.PAGE_VERSION;

        if (isSecured) {
            username = TestResources.USERNAME;
            password = TestResources.PASSWORD;
        }

        request = new Requests(url);

        if (isSecured) {
            request.setAuthentication(username, password);
        }
    }

    public void testRequestPages() throws Throwable
    {
        Pages pages = request.requestAllPages(wikiName, spaceName);
        assertNotNull(pages);
    }

    public void testRequestPage() throws Throwable
    {
        Page page = request.requestPage(wikiName, spaceName, pageName);
        assertNotNull(page);
    }

    public void testAddPage() throws Throwable
    {
        Page page = new Page();
        page.setContent("Content is changed by android client");
        page.setTitle("new page");
        String s = request.addPage(wikiName, spaceName, page.getTitle(), page);
        assertNotNull(s);
    }

    public void testRequestPageChildren() throws Throwable
    {
        Pages pages = request.requestPageChildren(wikiName, spaceName, pageName);
        assertNotNull(pages);
    }

    public void testRequestPageInVersionHistory() throws Throwable
    {
        Page page = request.requestPageInVersionHistory(wikiName, spaceName, pageName, version);
        assertNotNull(page);
    }

    public void testRequestPageTranslation() throws Throwable
    {
        Page page = request.requestPageTranslation(wikiName, spaceName, pageName, language);
        assertNotNull(page);
    }

    public void testRequestPageTranslationInHistory() throws Throwable
    {
        Page page = request.requestPageTranslationInHistory(wikiName, spaceName, pageName, language, version);
        assertNotNull(page);
    }

    public void testDeletePage() throws Throwable
    {
        String s = request.deletePage(wikiName, spaceName, pageName);
        assertNotNull(s);
    }

    public void testDeletePageInLanguage() throws Throwable
    {
        String s = request.deletePage(wikiName, spaceName, pageName, language);
        assertNotNull(s);
    }
}
