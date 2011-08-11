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

package org.xwiki.android.rest;

import java.io.InputStream;
import java.io.StringWriter;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xwiki.android.resources.Attachments;

/**
 * XWiki Android REST Attachment related source. Can get the attachment details/list as a Attachments objects or the
 * Stream of raw data of a attachment file. 
 */
public class AttachmentResources extends HttpConnector
{
    /**
     * Path provided from XWiki RESTful API
     */
    private final String PAGE_URL_PREFIX = "/xwiki/rest/wikis/";

    /**
     * URL of the XWiki domain
     */
    private String URLprefix;

    /**
     * Name of Wiki for acquiring attachment
     */
    private String wikiName;

    /**
     * Name of Space for acquiring attachment
     */
    private String spaceName;

    /**
     * Name of Page for acquiring attachment
     */
    private String pageName;

    /**
     * @param URLprefix XWiki URl ex:"www.xwiki.org"
     * @param wikiName name of the wiki in UTF-8 format
     * @param spaceName name of the space in UTF-8 format
     * @param pageName name of the page in UTF-8 format
     */
    public AttachmentResources(String URLprefix, String wikiName, String spaceName, String pageName)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
        this.spaceName = spaceName;
        this.pageName = pageName;
    }

    /**
     * Gets Attachments object according to the provided field values in constructor
     * 
     * @return Attachments object acquired according to the wiki, space & page names
     */
    public Attachments getAllPageAttachments()
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/attachments";

        return buildAttachments(super.getResponse(Uri));
    }

    /**
     * Gets a stream data of the requested attachment
     * 
     * @param attachmentName filename of the attachment in the page
     * @return InputStream of the attachment data
     */
    public InputStream getPageAttachment(String attachmentName)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/attachments/" + attachmentName;

        return super.getResponseAttachment(Uri);
    }

    /**
     * Add file to the XWiki stored in the internal/external memory
     * 
     * @param filePath absolute path of the file ex: "/sdcard/data/"
     * @param attachmentName name of the file name
     * @return status of the HTTP response
     */
    public String putPageAttachment(String filePath, String attachmentName)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/attachments/" + attachmentName;

        return super.putRaw(Uri, filePath + attachmentName);
    }

    /**
     * Gets Attachments object in the provided version of page
     * 
     * @param version version of the page
     * @return Attachments object acquired according to the wiki, space & page names
     */
    public Attachments getPageAttachmentsInHistory(String version)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/history/" + version + "/attachments";

        return buildAttachments(super.getResponse(Uri));
    }

    /**
     * Gets a stream data of the requested attachment in the provided page version
     * 
     * @param version version of the page
     * @param attachmentName file name of the attachement
     * @return InputStream of the attachment data in provided page version
     */
    public InputStream getPageAttachmentsInHistory(String version, String attachmentName)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/history/" + version + "/attachments/" + attachmentName;

        return super.getResponseAttachment(Uri);
    }

    /**
     * Gets the history of the attachment
     * 
     * @param attachmentName filename of the attachment
     * @return Attachments object with the version details of the attachment
     */
    public Attachments getPageAttachmentsInAttachmentHistory(String attachmentName)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/attachments/" + attachmentName + "/history";

        return buildAttachments(super.getResponse(Uri));
    }

    /**
     * Gets the attachment of a specific attachment version
     * 
     * @param attachmentName filename of the attachment
     * @param attachmentVersion version of the attachment
     * @return InputStream of the provided attachment version
     */
    public InputStream getPageAttachmentsInAttachmentHistory(String attachmentName, String attachmentVersion)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/attachments/" + attachmentName + "/history/" + attachmentVersion;

        return super.getResponseAttachment(Uri);
    }

    /**
     * Deletes attachment file from the XWiki server
     * 
     * @param attachmentName filename of the attachment
     * @return Status code of the HTTP response
     */
    public String deletePageAttachment(String attachmentName)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/attachments/" + attachmentName;

        return super.deleteRequest(Uri);
    }

    /**
     * Parse xml into a Attachments object
     * 
     * @param content XML content
     * @return Attachments object deserialized from the xml content
     */
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

    /**
     * Generate XML content from the Attachments object
     * 
     * @param attachments Attachments Object to be serialized into XML
     * @return XML content of the provided Attachments object
     */
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
