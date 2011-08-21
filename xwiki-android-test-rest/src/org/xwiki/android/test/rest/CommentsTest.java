/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

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
