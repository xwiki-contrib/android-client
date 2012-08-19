package org.xwiki.android.rest.rpc;

import java.io.InputStream;

import org.xwiki.android.resources.Attachments;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;

public interface AttachmentOperations
{

    /**
     * Gets Attachments object according to the provided field values in constructor
     * 
     * @return Attachments object acquired according to the wiki, space & page names
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract Attachments getAllPageAttachments() throws RestConnectionException, RestException;

    /**
     * Gets a stream data of the requested attachment
     * 
     * @param attachmentName filename of the attachment in the page
     * @return InputStream of the attachment data
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract InputStream getPageAttachment(String attachmentName) throws RestConnectionException, RestException;

    /**
     * Add file to the XWiki stored in the internal/external memory
     * 
     * @param filePath absolute path of the file ex: "/sdcard/data/"
     * @param attachmentName name of the file name
     * @return status of the HTTP response
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract String putPageAttachment(String filePath, String attachmentName) throws RestConnectionException,
        RestException;

    /**
     * Gets Attachments object in the provided version of page
     * 
     * @param version version of the page
     * @return Attachments object acquired according to the wiki, space & page names
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract Attachments getPageAttachmentsInHistory(String version) throws RestConnectionException,
        RestException;

    /**
     * Gets a stream data of the requested attachment in the provided page version
     * 
     * @param version version of the page
     * @param attachmentName file name of the attachement
     * @return InputStream of the attachment data in provided page version
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract InputStream getPageAttachmentsInHistory(String version, String attachmentName)
        throws RestConnectionException, RestException;

    /**
     * Gets the history of the attachment
     * 
     * @param attachmentName filename of the attachment
     * @return Attachments object with the version details of the attachment
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract Attachments getPageAttachmentsInAttachmentHistory(String attachmentName)
        throws RestConnectionException, RestException;

    /**
     * Gets the attachment of a specific attachment version
     * 
     * @param attachmentName filename of the attachment
     * @param attachmentVersion version of the attachment
     * @return InputStream of the provided attachment version
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract InputStream getPageAttachmentsInAttachmentHistory(String attachmentName, String attachmentVersion)
        throws RestConnectionException, RestException;

    /**
     * Deletes attachment file from the XWiki server
     * 
     * @param attachmentName filename of the attachment
     * @return Status code of the HTTP response
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract String deletePageAttachment(String attachmentName) throws RestConnectionException, RestException;

}
