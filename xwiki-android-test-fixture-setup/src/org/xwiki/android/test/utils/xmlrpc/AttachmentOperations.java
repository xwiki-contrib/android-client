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

package org.xwiki.android.test.utils.xmlrpc;

import org.apache.http.util.EntityUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xwiki.android.resources.Attachments;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * XWiki Android REST Attachment related source. Can get the attachment details/list as a Attachments objects or the
 * Stream of raw data of a attachment file.
 */
public class AttachmentOperations {
    
    RestClient rpc;
    
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
     * @param wikiName  name of the wiki in UTF-8 format
     * @param spaceName name of the space in UTF-8 format
     * @param pageName  name of the page in UTF-8 format
     */
    AttachmentOperations(String URLprefix, String wikiName, String spaceName, String pageName, RestClient rpc) {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
        this.spaceName = spaceName;
        this.pageName = pageName;
        this.rpc=rpc;
    }

    /**
     * Gets Attachments object according to the provided field values in constructor
     *
     * @return Attachments object acquired according to the wiki, space & page names
     * @throws IOException
     * @throws RestException
     */
    public Attachments getAllPageAttachments() throws IOException, RestException {
        String Uri =
                "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                        + "/attachments";

        return buildAttachments(EntityUtils.toString(rpc.getRequest(Uri).getEntity()));
    }

    /**
     * Gets a stream data of the requested attachment
     *
     * @param attachmentName filename of the attachment in the page
     * @return InputStream of the attachment data
     * @throws IOException
     * @throws RestException
     */
    public InputStream getPageAttachment(String attachmentName) throws IOException, RestException {
        String Uri =
                "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                        + "/attachments/" + attachmentName;

        return rpc.getRequest(Uri).getEntity().getContent();
    }

    /**
     * Add file to the XWiki stored in the internal/external memory
     *
     * @param file           file representing the attachement.
     * @param attachmentName    name for the attachment
     * @return status of the HTTP response
     * @throws IOException
     * @throws RestException
     */
    public String putPageAttachment(String attachmentName, File file) throws IOException, RestException {
        String Uri =
                "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                        + "/attachments/" + attachmentName;

        return rpc.putRequest(Uri, file).getStatusLine().toString();
    }

    /**
     * Gets Attachments object in the provided version of page
     *
     * @param version version of the page
     * @return Attachments object acquired according to the wiki, space & page names
     * @throws IOException
     * @throws RestException
     */
    public Attachments getPageAttachmentsInHistory(String version) throws IOException, RestException {
        String Uri =
                "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                        + "/history/" + version + "/attachments";

        return buildAttachments(EntityUtils.toString(rpc.getRequest(Uri).getEntity()));
    }

    /**
     * Gets a stream data of the requested attachment in the provided page version
     *
     * @param version        version of the page
     * @param attachmentName file name of the attachement
     * @return InputStream of the attachment data in provided page version
     * @throws IOException
     * @throws RestException
     */
    public InputStream getPageAttachmentsInHistory(String version, String attachmentName) throws IOException, RestException {
        String Uri =
                "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                        + "/history/" + version + "/attachments/" + attachmentName;

        return rpc.getRequest(Uri).getEntity().getContent();
    }

    /**
     * Gets the history of the attachment
     *
     * @param attachmentName filename of the attachment
     * @return Attachments object with the version details of the attachment
     * @throws IOException
     * @throws RestException
     */
    public Attachments getPageAttachmentsInAttachmentHistory(String attachmentName) throws IOException, RestException {
        String Uri =
                "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                        + "/attachments/" + attachmentName + "/history";

        return buildAttachments(EntityUtils.toString(rpc.getRequest(Uri).getEntity()));
    }

    /**
     * Gets the attachment of a specific attachment version
     *
     * @param attachmentName    filename of the attachment
     * @param attachmentVersion version of the attachment
     * @return InputStream of the provided attachment version
     * @throws IOException
     * @throws RestException
     */
    public InputStream getPageAttachmentsInAttachmentHistory(String attachmentName, String attachmentVersion) throws IOException, RestException {
        String Uri =
                "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                        + "/attachments/" + attachmentName + "/history/" + attachmentVersion;

        return rpc.getRequest(Uri).getEntity().getContent();
    }

    /**
     * Deletes attachment file from the XWiki server
     *
     * @param attachmentName filename of the attachment
     * @return Status line of Http response
     * @throws IOException
     * @throws RestException
     */
    public String deletePageAttachment(String attachmentName) throws IOException, RestException {
        String Uri =
                "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                        + "/attachments/" + attachmentName;

        return rpc.deleteRequest(Uri).getStatusLine().toString();
    }

    /**
     * Parse xml into a Attachments object
     *
     * @param content XML content
     * @return Attachments object deserialized from the xml content
     */
    private Attachments buildAttachments(String content) {
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
    private String buildXmlAttachments(Attachments attachments) {
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
