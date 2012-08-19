package org.xwiki.android.rest.rpc;

import java.io.InputStream;

import org.xwiki.android.resources.Attachments;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;


class _AttachmentOperations implements AttachmentOperations
{
    
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
    _AttachmentOperations(String URLprefix, String wikiName, String spaceName, String pageName, RestClient rpc) {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
        this.spaceName = spaceName;
        this.pageName = pageName;
        this.rpc=rpc;
    }

    @Override
    public Attachments getAllPageAttachments() throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public InputStream getPageAttachment(String attachmentName) throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String putPageAttachment(String filePath, String attachmentName) throws RestConnectionException,
        RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Attachments getPageAttachmentsInHistory(String version) throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public InputStream getPageAttachmentsInHistory(String version, String attachmentName)
        throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Attachments getPageAttachmentsInAttachmentHistory(String attachmentName) throws RestConnectionException,
        RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public InputStream getPageAttachmentsInAttachmentHistory(String attachmentName, String attachmentVersion)
        throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String deletePageAttachment(String attachmentName) throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

}
