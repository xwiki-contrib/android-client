package org.xwiki.android.test.rest;

import org.xwiki.android.resources.Comment;
import org.xwiki.android.resources.Comments;
import org.xwiki.android.rest.Requests;

import android.test.AndroidTestCase;

public class CommentsTest extends AndroidTestCase
{
    private String wikiName, spaceName, pageName, url, username, password, commentId, version;

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
        commentId = TestResources.COMMENT_ID;
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

    public void testRequestPageComments() throws Throwable
    {

        Comments cs = request.requestPageComments(wikiName, spaceName, pageName);
        assertNotNull(cs);
    }

    public void testRequestPageComment() throws Throwable
    {

        Comment c = request.requestPageComments(wikiName, spaceName, pageName, commentId);
        assertNotNull(c);
    }

    public void testRequestCommentsInHistory() throws Throwable
    {
        Comments cs = request.requestPageCommentsInHistory(wikiName, spaceName, pageName, version);
        assertNotNull(cs);
    }

    public void testRequestCommentsInHistoryWithID() throws Throwable
    {
        Comments cs = request.requestPageCommentsInHistory(wikiName, spaceName, pageName, version, commentId);
        assertNotNull(cs);
    }

    public void testAddComment() throws Throwable
    {
        Comment comment = new Comment();
        comment.setText("This is tesing comment in android rest");
        String s = request.addPageComment(wikiName, spaceName, pageName, comment);
        assertNotNull(s);
    }

}
