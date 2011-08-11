package org.xwiki.android.test.rest;

import org.xwiki.android.resources.Comments;
import org.xwiki.android.rest.Requests;

import android.test.AndroidTestCase;

public class CommentsTest extends AndroidTestCase
{
    public static final String WIKI_NAME = "xwiki";

    public static final String SPACE_NAME = "Blog";

    public static final String PAGE_NAME = "test";

    public static final String URL = "10.0.2.2:8080";

    public static final String USERNAME = "Admin";

    public static final String PASSWORD = "admin";

    private String wikiName, spaceName, pageName, url, username, password;

    private boolean isSecured = true;

    private Requests request;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        wikiName = WIKI_NAME;
        spaceName = SPACE_NAME;
        pageName = PAGE_NAME;
        url = URL;

        if (isSecured) {
            username = USERNAME;
            password = PASSWORD;
        }

        request = new Requests(url);

        if (isSecured) {
            request.setAuthentication(username, password);
        }
    }

    public void testRequestComments() throws Throwable
    {

        Comments cs = request.requestPageComments(wikiName, spaceName, pageName);
        assertNotNull(cs);
    }

}
