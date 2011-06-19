package org.xwiki.android.rest;

import org.xwiki.android.resources.Attachments;
import org.xwiki.android.resources.Wikis;

import com.google.gson.Gson;

public class AttachmentResources extends HttpConnector
{

    private final String PAGE_URL_PREFIX = "/xwiki/rest/wikis/";

    private final String JSON_URL_SUFFIX = "?media=json";

    private String URLprefix;

    private String wikiName;

    private String spaceName;

    private String pageName;

    public AttachmentResources(String URLprefix, String wikiName, String spaceName, String pageName)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
        this.spaceName = spaceName;
        this.pageName = pageName;
    }

    public Attachments getAllPageAttachments()
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/attachments" + JSON_URL_SUFFIX;

        return decodeAttachments(super.getResponse(Uri));
    }

    // should return attachment bytes
    public String getPageAttachment(String attachmentName)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/attachments/" + attachmentName + JSON_URL_SUFFIX;

        return super.getResponse(Uri);
    }

    // get attachments of page history
    public Attachments getPageAttachmentsInHistory(String version)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/history/" + version + "/attachments" + JSON_URL_SUFFIX;

        return decodeAttachments(super.getResponse(Uri));
    }

    // get Attachment in specific page version
    public String getPageAttachmentsInHistory(String version, String attachmentName)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/history/" + version + "/attachments/" + attachmentName + JSON_URL_SUFFIX;

        return super.getResponse(Uri);
    }

    // get attachments history of a attachment
    public Attachments getPageAttachmentsInAttachmentHistory(String attachmentName)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/attachments/" + attachmentName + "/history" + JSON_URL_SUFFIX;

        return decodeAttachments(super.getResponse(Uri));
    }

    //get attachment of a specific attachment history
    public String getPageAttachmentsInAttachmentHistory(String attachmentName, String attachmentVersion)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/attachments/" + attachmentName + "/history/" + attachmentVersion + JSON_URL_SUFFIX;

        return super.getResponse(Uri);
    }

    // decode json content to Wikis
    private Attachments decodeAttachments(String content)
    {
        Gson gson = new Gson();

        Attachments attachments = new Attachments();
        attachments = gson.fromJson(content, Attachments.class);
        return attachments;
    }

}
