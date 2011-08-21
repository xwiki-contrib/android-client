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

import org.xwiki.android.resources.Attachments;
import org.xwiki.android.rest.Requests;

import android.test.AndroidTestCase;

public class AttachmentTest extends AndroidTestCase
{
    private String wikiName, spaceName, pageName, url, username, password, version, filePath, attachmentName,
        attachmentVersion;

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
        version = TestResources.PAGE_VERSION;
        attachmentName = TestResources.ATTACHMENT_NAME;
        filePath = TestResources.ATTACHMENT_PATH;
        attachmentVersion = TestResources.ATTACHMENT_VERSION;

        if (isSecured) {
            username = TestResources.USERNAME;
            password = TestResources.PASSWORD;
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
        String s = request.deletePageAttachment(wikiName, spaceName, pageName, attachmentName);
        assertNotNull(s);
    }

    public void testRequestPageAttchmentsInHistory() throws Throwable
    {
        Attachments attachments = request.requestPageAttachmentsInHistory(wikiName, spaceName, pageName, version);
        assertNotNull(attachments);
    }

    // public void testRequestPageAttachmentInHistory() throws Throwable
    // {
    // InputStream inputStream =
    // request.requestPageAttachmentsInHistory(wikiName, spaceName, pageName, version, attachmentName);
    // assertNotNull(inputStream);
    // }

    public void testRequestPageAttchmentsInAttachmentHistory() throws Throwable
    {
        Attachments attachments =
            request.requestPageAttachmentsInAttachmentHistory(wikiName, spaceName, pageName, attachmentName);
        assertNotNull(attachments);
    }

    // public void testRequestPageAttachmentInAttachmentHistory() throws Throwable
    // {
    // InputStream inputStream =
    // request.requestPageAttachmentsInAttachmentHistory(wikiName, spaceName, pageName, attachmentName,
    // attachmentVersion);
    // assertNotNull(inputStream);
    // }

}
