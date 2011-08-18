package org.xwiki.android.test.rest;

import java.io.InputStream;

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

    public void testRequestPageAttachment() throws Throwable
    {
        InputStream inputStream = request.requestPageAttachment(wikiName, spaceName, pageName, attachmentName);
        assertNotNull(inputStream);
    }

    //Need to add file to the device "filpath + attachmentName" to work
    // public void testAddPageAttachment() throws Throwable
    // {
    // String s = request.addPageAttachment(wikiName, spaceName, pageName, filePath, attachmentName);
    // assertNotNull(s);
    // }

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

    public void testRequestPageAttachmentInHistory() throws Throwable
    {
        InputStream inputStream =
            request.requestPageAttachmentsInHistory(wikiName, spaceName, pageName, version, attachmentName);
        assertNotNull(inputStream);
    }

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
