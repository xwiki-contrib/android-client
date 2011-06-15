package org.xwiki.android.rest;

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

    public String getAllPageAttachments()
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/attachments" + JSON_URL_SUFFIX;

        return super.getResponse(Uri);
    }

    public String getPageAttachment(String attachmentName)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/attachments/" + attachmentName + JSON_URL_SUFFIX;

        return super.getResponse(Uri);
    }

    // get attachments of page history
    public String getPageAttachmentsInHistory(String version)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/history/" + version + "/attachments" + JSON_URL_SUFFIX;

        return super.getResponse(Uri);
    }

    public String getPageAttachmentsInHistory(String version, String attachmentName)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/history/" + version + "/attachments/" + attachmentName + JSON_URL_SUFFIX;

        return super.getResponse(Uri);
    }

    // get attachment of attachment history
    public String getPageAttachmentsInAttachmentHistory(String attachmentName)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/attachments/" + attachmentName + "/history" + JSON_URL_SUFFIX;

        return super.getResponse(Uri);
    }

    public String getPageAttachmentsInAttachmentHistory(String attachmentName, String attachmentVersion)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/attachments/" + attachmentName + "/history/" + attachmentVersion + JSON_URL_SUFFIX;

        return super.getResponse(Uri);
    }
    
}
