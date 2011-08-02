package org.xwiki.android.rest;

import java.io.InputStream;
import java.io.StringWriter;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xwiki.android.resources.Attachments;

public class AttachmentResources extends HttpConnector
{

    private final String PAGE_URL_PREFIX = "/xwiki/rest/wikis/";

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
                + "/attachments";

        return buildAttachments(super.getResponse(Uri));
    }

    // should return attachment bytes [ok]
    public InputStream getPageAttachment(String attachmentName)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/attachments/" + attachmentName;

        return super.getResponseAttachment(Uri);
    }

    // add Attachment
    public String putPageAttachment(String filePath, String attachmentName)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/attachments/" + attachmentName;

        return super.putRaw(Uri, filePath + attachmentName);
    }

    // get attachments of page history
    public Attachments getPageAttachmentsInHistory(String version)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/history/" + version + "/attachments";

        return buildAttachments(super.getResponse(Uri));
    }

    // get Attachment in specific page version
    public InputStream getPageAttachmentsInHistory(String version, String attachmentName)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/history/" + version + "/attachments/" + attachmentName;

        return super.getResponseAttachment(Uri);
    }

    // get attachments history of a attachment
    public Attachments getPageAttachmentsInAttachmentHistory(String attachmentName)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/attachments/" + attachmentName + "/history";

        return buildAttachments(super.getResponse(Uri));
    }

    // get attachment of a specific attachment history
    public InputStream getPageAttachmentsInAttachmentHistory(String attachmentName, String attachmentVersion)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/attachments/" + attachmentName + "/history/" + attachmentVersion;

        return super.getResponseAttachment(Uri);
    }

    // delete specific attachment
    public String deletePageAttachment(String attachmentName)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/attachments/" + attachmentName;

        return super.deleteRequest(Uri);
    }

    // decode xml content to Comments element
    private Attachments buildAttachments(String content)
    {
        Attachments attachments = new Attachments();

        Serializer serializer = new Persister();

        try {
            attachments = serializer.read(Attachments.class, content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return attachments;
    }

    // build xml from Comments object
    private String buildXmlAttachments(Attachments attachments)
    {
        Serializer serializer = new Persister();

        StringWriter result = new StringWriter();

        try {
            serializer.write(attachments, result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result.toString();
    }

}
