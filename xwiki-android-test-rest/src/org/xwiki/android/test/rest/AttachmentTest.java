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

import java.io.InputStream;

import org.xwiki.android.resources.Attachments;
import org.xwiki.android.rest.Requests;
import org.xwiki.android.test.rest.env.TestConstants;

import android.test.AndroidTestCase;

public class AttachmentTest extends AndroidTestCase
{
    private String wikiName, spaceName, pageName, url, username, password, pageVersion, filePath, attachmentName,
        attachmentVersion,attachmentDelName;

    private boolean isSecured = true;

    private Requests request;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        wikiName = TestConstants.WIKI_NAME;
        spaceName = TestConstants.SPACE_NAME;
        pageName = TestConstants.PAGE_NAME;
        url = TestConstants.SERVER_URL;
        pageVersion = TestConstants.ATTACHMENT_PAGE_HISTORY_VERSION;
        attachmentName = TestConstants.ATTACHMENT_NAME;
        filePath = TestConstants.ATTACHMENT_PATH;
        attachmentVersion = TestConstants.ATTACHMENT_VERSION;
        attachmentDelName= TestConstants.ATTACHMENT_DEL_NAME;

        if (isSecured) {
            username = TestConstants.USERNAME;
            password = TestConstants.PASSWORD;
        }

        request = new Requests(url);

        if (isSecured) {
            request.setAuthentication(username, password);
        }
    }

    public void testRequestAllPageAttachments() throws Throwable
    {
        Attachments attachments = request.requestAllPageAttachments(wikiName, spaceName, pageName);
        assertNotNull(attachments);
    }

    // public void testRequestPageAttachment() throws Throwable
    // {
    // InputStream inputStream = request.requestPageAttachment(wikiName, spaceName, pageName, attachmentName);
    // assertNotNull(inputStream);
    // }

    public void testAddPageAttachment() throws Throwable
    {
        String s = request.addPageAttachment(wikiName, spaceName, pageName, filePath, attachmentName);
        assertNotNull(s);
    }

    public void testDeletePageAttachment() throws Throwable
    {
        String s = request.deletePageAttachment(wikiName, spaceName, pageName, attachmentDelName);
        assertNotNull(s);
    }

    public void testRequestPageAttchmentsInHistory() throws Throwable
    {
        Attachments attachments = request.requestPageAttachmentsInHistory(wikiName, spaceName, pageName, pageVersion);
        assertNotNull(attachments);
    }

//     public void testRequestPageAttachmentInHistory() throws Throwable
//     {
//     InputStream inputStream =
//     request.requestPageAttachmentsInHistory(wikiName, spaceName, pageName, pageVersion, attachmentName);
//     assertNotNull(inputStream);
//     } // this test is not working in xwiki 3.5.1. Server Error.

    public void testRequestPageAttchmentsInAttachmentHistory() throws Throwable
    {
        Attachments attachments =
            request.requestPageAttachmentsInAttachmentHistory(wikiName, spaceName, pageName, attachmentName);
        assertNotNull(attachments);
    }

     public void testRequestPageAttachmentInAttachmentHistory() throws Throwable
     {
     InputStream inputStream =
     request.requestPageAttachmentsInAttachmentHistory(wikiName, spaceName, pageName, attachmentName,
     attachmentVersion);
     assertNotNull(inputStream);
     }

}
