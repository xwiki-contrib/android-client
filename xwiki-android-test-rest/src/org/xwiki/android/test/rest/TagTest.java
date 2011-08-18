package org.xwiki.android.test.rest;

import org.xwiki.android.resources.Tag;
import org.xwiki.android.resources.Tags;
import org.xwiki.android.rest.Requests;

import android.test.AndroidTestCase;

public class TagTest extends AndroidTestCase
{
    private String wikiName, spaceName, pageName, url, username, password, tagText;

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
        tagText = TestResources.TAG_WORD;
        
        if (isSecured) {
            username = TestResources.USERNAME;
            password = TestResources.PASSWORD;
        }

        request = new Requests(url);

        if (isSecured) {
            request.setAuthentication(username, password);
        }
    }

    public void testRequestPageTags() throws Throwable
    {
        Tags tags = request.requestPageTags(wikiName, spaceName, pageName);
        assertNotNull(tags);
    }

    
    public void testAddPageTags() throws Throwable
    {
        Tag tag = new Tag();
        tag.name = tagText;
        String s = request.addPageTag(wikiName, spaceName, pageName, tag);
        assertNotNull(s);
    }
    
    public void testRequestWikiTags() throws Throwable
    {
        Tags tags = request.requestWikiTags(wikiName);
        assertNotNull(tags);
    }
}
