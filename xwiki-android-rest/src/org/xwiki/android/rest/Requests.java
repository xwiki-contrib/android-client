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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.xwiki.android.resources.Attachments;
import org.xwiki.android.resources.Class;
import org.xwiki.android.resources.Classes;
import org.xwiki.android.resources.Comment;
import org.xwiki.android.resources.Comments;
import org.xwiki.android.resources.History;
import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.Objects;
import org.xwiki.android.resources.Page;
import org.xwiki.android.resources.Pages;
import org.xwiki.android.resources.Properties;
import org.xwiki.android.resources.Property;
import org.xwiki.android.resources.SearchResults;
import org.xwiki.android.resources.Spaces;
import org.xwiki.android.resources.Tag;
import org.xwiki.android.resources.Tags;
import org.xwiki.android.resources.Translations;
import org.xwiki.android.resources.Wikis;

import android.util.Log;

/**
 * All the XWiki Android RESTful requests for getting, putting and posting are included here. Method names starting from
 * "request" will return objects XWiki Android REST model. Currently using xwiki-xmlrpc-model-simplexml. Method names
 * starting from "add" will add XWiki Android REST model objects to the XWiki Server.
 */
@Deprecated
public class Requests
{
    /**
     * URL of the XWiki domain
     */
    private String URLprefix;

    /**
     * Username for accessing XWiki
     */
    private String username;

    /**
     * Password for accessing XWiki
     */
    private String password;

    /**
     * State of the requests has user authentication or not
     */
    private boolean isAuthenticated = false;

    /**
     * @param URLprefix URL of the XWiki domain
     */
    public Requests(String URLprefix)
    {
        this.URLprefix = URLprefix;
        isAuthenticated = false;
    }

    /**
     * Constructor for directly setting user credentials.
     * 
     * @param URLprefix URL of the XWiki domain
     * @param username username of the user account
     * @param password password of the user account
     */
    public Requests(String URLprefix, String username, String password)
    {
        this.URLprefix = URLprefix;
        this.username = username;
        this.password = password;
        isAuthenticated = true;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#setAuthentication(java.lang.String, java.lang.String)
	 */
    public void setAuthentication(String username, String password)
    {
        this.username = username;
        this.password = password;
        isAuthenticated = true;
    }

    /**
     * Converts strings to UCS Transformation Format - 8-bit(UTF-8) for calling XWiki RESTful API
     * Does URL encoding of the keyword.
     * @param keyword text to convert
     * @return converted text
     */
    private String convertToUTF(String keyword)
    {

        // Convert keyword to UTF-8
        try {
            keyword = URLEncoder.encode(keyword, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            Log.d("Error", "Unsupported keyword is found");
            keyword = "error in converting to UTF-8";
            e.printStackTrace();
        }

        return keyword;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestSearch(java.lang.String, java.lang.String)
	 */
    public SearchResults requestSearch(String wikiName, String keyword)
    {

        wikiName = convertToUTF(wikiName);
        keyword = convertToUTF(keyword);

        Search search = new Search(URLprefix);

        if (isAuthenticated) {
            search.setAuthenticaion(username, password);
        }

        try {
            return search.doWikiSearch(wikiName, keyword);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestSearch(java.lang.String, java.lang.String, java.lang.String)
	 */
    
    public SearchResults requestSearch(String wikiName, String spaceName, String keyword)
    {

        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        keyword = convertToUTF(keyword);

        Search search = new Search(URLprefix);
        if (isAuthenticated) {
            search.setAuthenticaion(username, password);
        }
        try {
            return search.doSpacesSearch(wikiName, spaceName, keyword);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestWikis()
	 */
    
    public Wikis requestWikis()
    {

        WikiResources wikiresources = new WikiResources(URLprefix);
        if (isAuthenticated) {
            wikiresources.setAuthenticaion(username, password);
        }
        Wikis wikis=null;
        try {
            wikis = wikiresources.getWikis();
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return wikis;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestSpaces(java.lang.String)
	 */
    
    public Spaces requestSpaces(String wikiName)
    {
        wikiName = convertToUTF(wikiName);
        SpaceResources spacesresources = new SpaceResources(URLprefix, wikiName);
        if (isAuthenticated) {
            spacesresources.setAuthenticaion(username, password);
        }
        try {
            return spacesresources.getSpaces();
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestAllPages(java.lang.String, java.lang.String)
	 */
    
    public Pages requestAllPages(String wikiName, String spaceName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pagesresources.setAuthenticaion(username, password);
        }
        try {
            return pagesresources.getAllPages();
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestPage(java.lang.String, java.lang.String, java.lang.String)
	 */
    
    public Page requestPage(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pagesresources.setAuthenticaion(username, password);
        }
        try {
            return pagesresources.getPage(pageName);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#addPage(java.lang.String, java.lang.String, java.lang.String, org.xwiki.android.resources.Page)
	 */
    
    public String addPage(String wikiName, String spaceName, String pageName, Page page)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pagesresources.setAuthenticaion(username, password);
        }
        try {
            return pagesresources.addPage(page);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "error";
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#deletePage(java.lang.String, java.lang.String, java.lang.String)
	 */
    
    public String deletePage(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pagesresources.setAuthenticaion(username, password);
        }
        try {
            return pagesresources.deletePage(pageName);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "error";
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#deletePage(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    
    public String deletePage(String wikiName, String spaceName, String pageName, String language)

    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pagesresources.setAuthenticaion(username, password);
        }
        try {
            return pagesresources.deletePageTranslation(pageName, language);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "error";
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestPageHistory(java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public History requestPageHistory(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        HistoryResources historyresources = new HistoryResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            historyresources.setAuthenticaion(username, password);
        }
        try {
            return historyresources.getPageHistory();
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestPageInVersionHistory(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public Page requestPageInVersionHistory(String wikiName, String spaceName, String pageName, String version)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pagesresources.setAuthenticaion(username, password);
        }
        try {
            return pagesresources.getPageHistoryOnVersion(pageName, version);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestPageChildren(java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public Pages requestPageChildren(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pagesresources.setAuthenticaion(username, password);
        }
        try {
            return pagesresources.getPageChildren(pageName);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestPageTags(java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public Tags requestPageTags(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        TagResources tagresources = new TagResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            tagresources.setAuthenticaion(username, password);
        }
        try {
            return tagresources.getTags();
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#addPageTag(java.lang.String, java.lang.String, java.lang.String, org.xwiki.android.resources.Tag)
	 */
    
	
    public String addPageTag(String wikiName, String spaceName, String pageName, Tag tag)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        TagResources tagresources = new TagResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            tagresources.setAuthenticaion(username, password);
        }
        try {
            return tagresources.addTag(tag);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "error";

    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestWikiTags(java.lang.String)
	 */
    
	
    public Tags requestWikiTags(String wikiName)
    {
        wikiName = convertToUTF(wikiName);

        TagResources tagresources = new TagResources(URLprefix, wikiName);
        if (isAuthenticated) {
            tagresources.setAuthenticaion(username, password);
        }
        try {
            return tagresources.getTags();
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestPageComments(java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public Comments requestPageComments(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        CommentResources commentresources = new CommentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            commentresources.setAuthenticaion(username, password);
        }
        try {
            return commentresources.getPageComments();
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#addPageComment(java.lang.String, java.lang.String, java.lang.String, org.xwiki.android.resources.Comment)
	 */
    
	
    public String addPageComment(String wikiName, String spaceName, String pageName, Comment comment)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        CommentResources commentresources = new CommentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            commentresources.setAuthenticaion(username, password);
            try {
                return commentresources.addPageComment(comment);
            } catch (RestConnectionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (RestException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            return "Autherization details are not provided";
        }
        return "error";
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestPageComments(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public Comment requestPageComments(String wikiName, String spaceName, String pageName, String commentId)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        CommentResources commentresources = new CommentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            commentresources.setAuthenticaion(username, password);
        }
        try {
            return commentresources.getPageComment(new Integer(commentId));
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestPageCommentsInHistory(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public Comments requestPageCommentsInHistory(String wikiName, String spaceName, String pageName, String version)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        CommentResources commentresources = new CommentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            commentresources.setAuthenticaion(username, password);
        }
        try {
            return commentresources.getPageCommentsInHistory(version);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestPageCommentsInHistory(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public Comments requestPageCommentsInHistory(String wikiName, String spaceName, String pageName, String version,
        String commentId)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        CommentResources commentresources = new CommentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            commentresources.setAuthenticaion(username, password);
        }
        try {
            return commentresources.getPageCommentsInHistory(version, commentId);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestAllPageAttachments(java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public Attachments requestAllPageAttachments(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            attachmentresources.setAuthenticaion(username, password);
        }

        try {
            return attachmentresources.getAllPageAttachments();
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestPageAttachment(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public InputStream requestPageAttachment(String wikiName, String spaceName, String pageName, String attachmentName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);
        attachmentName = convertToUTF(attachmentName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            attachmentresources.setAuthenticaion(username, password);
        }
        try {
            return attachmentresources.getPageAttachment(attachmentName);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#addPageAttachment(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public String addPageAttachment(String wikiName, String spaceName, String pageName, String filePath,
        String attachmentName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);
        attachmentName = convertToUTF(attachmentName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            attachmentresources.setAuthenticaion(username, password);
        }
        try {
            return attachmentresources.putPageAttachment(filePath, attachmentName);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "error";
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#deletePageAttachment(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public String deletePageAttachment(String wikiName, String spaceName, String pageName, String attachmentName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);
        attachmentName = convertToUTF(attachmentName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            attachmentresources.setAuthenticaion(username, password);
        }
        try {
            return attachmentresources.deletePageAttachment(attachmentName);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "error";
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestPageAttachmentsInHistory(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public Attachments requestPageAttachmentsInHistory(String wikiName, String spaceName, String pageName,
        String pageVersion)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            attachmentresources.setAuthenticaion(username, password);
        }
        try {
            return attachmentresources.getPageAttachmentsInHistory(pageVersion);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestPageAttachmentsInHistory(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public InputStream requestPageAttachmentsInHistory(String wikiName, String spaceName, String pageName,
        String pageVersion, String attachmentName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);
        attachmentName = convertToUTF(attachmentName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            attachmentresources.setAuthenticaion(username, password);
        }
        try {
            return attachmentresources.getPageAttachmentsInHistory(pageVersion, attachmentName);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestPageAttachmentsInAttachmentHistory(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public Attachments requestPageAttachmentsInAttachmentHistory(String wikiName, String spaceName, String pageName,
        String attachmentName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);
        attachmentName = convertToUTF(attachmentName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            attachmentresources.setAuthenticaion(username, password);
        }
        try {
            return attachmentresources.getPageAttachmentsInAttachmentHistory(attachmentName);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestPageAttachmentsInAttachmentHistory(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public InputStream requestPageAttachmentsInAttachmentHistory(String wikiName, String spaceName, String pageName,
        String attachmentName, String attachmentVersion)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);
        attachmentName = convertToUTF(attachmentName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            attachmentresources.setAuthenticaion(username, password);
        }
        try {
            return attachmentresources.getPageAttachmentsInAttachmentHistory(attachmentName, attachmentVersion);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestWikiClasses(java.lang.String)
	 */
    
	
    public Classes requestWikiClasses(String wikiName)
    {
        wikiName = convertToUTF(wikiName);

        ClassResources classresources = new ClassResources(URLprefix, wikiName);
        if (isAuthenticated) {
            classresources.setAuthenticaion(username, password);
        }
        try {
            return classresources.getWikiClasses();
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestWikiClasses(java.lang.String, java.lang.String)
	 */
    
	
    public Class requestWikiClasses(String wikiName, String classname)
    {
        wikiName = convertToUTF(wikiName);
        classname = convertToUTF(classname);

        ClassResources classresources = new ClassResources(URLprefix, wikiName);
        if (isAuthenticated) {
            classresources.setAuthenticaion(username, password);
        }
        try {
            return classresources.getWikiClasses(classname);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestAllPageTranslations(java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public Translations requestAllPageTranslations(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        TranslationResources translationresources = new TranslationResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            translationresources.setAuthenticaion(username, password);
        }
        try {
            return translationresources.getAllTranslations();
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestPageTranslation(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public Page requestPageTranslation(String wikiName, String spaceName, String pageName, String language)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        PageResources pageresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pageresources.setAuthenticaion(username, password);
        }
        try {
            return pageresources.getPageTranslation(pageName, language);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestPageHistoryInLanguage(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public History requestPageHistoryInLanguage(String wikiName, String spaceName, String pageName, String language)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        HistoryResources historyresources = new HistoryResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            historyresources.setAuthenticaion(username, password);
        }
        try {
            return historyresources.getPageHistory(language);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestPageTranslationInHistory(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public Page requestPageTranslationInHistory(String wikiName, String spaceName, String pageName, String language,
        String version)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        PageResources pageresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pageresources.setAuthenticaion(username, password);
        }
        try {
            return pageresources.getPageTranslation(pageName, language, version);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#addObject(java.lang.String, java.lang.String, java.lang.String, org.xwiki.android.resources.Object)
	 */
    
	
    public String addObject(String wikiName, String spaceName, String pageName, Object object)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        ObjectResources objectresources = new ObjectResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            objectresources.setAuthenticaion(username, password);
        }
        try {
            return objectresources.addObject(object);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "error";
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestAllObjects(java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public Objects requestAllObjects(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        ObjectResources objectresources = new ObjectResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            objectresources.setAuthenticaion(username, password);
        }
        try {
            return objectresources.getAllObjects();
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestObjectsInClass(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public Objects requestObjectsInClass(String wikiName, String spaceName, String pageName, String objectClassname)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);
        objectClassname = convertToUTF(objectClassname);

        ObjectResources objectresources = new ObjectResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            objectresources.setAuthenticaion(username, password);
        }
        try {
            return objectresources.getObjectsInClassname(objectClassname);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestObject(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public Object requestObject(String wikiName, String spaceName, String pageName, String objectClassname,
        String objectNumber)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);
        objectClassname = convertToUTF(objectClassname);
        objectNumber = convertToUTF(objectNumber);

        ObjectResources objectresources = new ObjectResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            objectresources.setAuthenticaion(username, password);
        }
        try {
            return objectresources.getObject(objectClassname, objectNumber);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#deleteObject(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public String deleteObject(String wikiName, String spaceName, String pageName, String objectClassname,
        String objectNumber)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);
        objectClassname = convertToUTF(objectClassname);
        objectNumber = convertToUTF(objectNumber);

        ObjectResources objectresources = new ObjectResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            objectresources.setAuthenticaion(username, password);
        }
        try {
            return objectresources.deleteObject(objectClassname, objectNumber);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "error";
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestObjectAllProperties(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public Properties requestObjectAllProperties(String wikiName, String spaceName, String pageName,
        String objectClassname, String objectNumber)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);
        objectClassname = convertToUTF(objectClassname);
        objectNumber = convertToUTF(objectNumber);

        ObjectResources objectresources = new ObjectResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            objectresources.setAuthenticaion(username, password);
        }
        try {
            return objectresources.getObjectProperties(objectClassname, objectNumber);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#requestObjectProperty(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    
	
    public Property requestObjectProperty(String wikiName, String spaceName, String pageName, String objectClassname,
        String objectNumber, String propertyName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);
        objectClassname = convertToUTF(objectClassname);
        objectNumber = convertToUTF(objectNumber);
        propertyName = convertToUTF(propertyName);

        ObjectResources objectresources = new ObjectResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            objectresources.setAuthenticaion(username, password);
        }
        try {
            return objectresources.getObjectProperty(objectClassname, objectNumber, propertyName);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.xmlrpc.XWikiRestfulAPI#addProperty(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, org.xwiki.android.resources.Property)
	 */
    
	
    public String addProperty(String wikiName, String spaceName, String pageName, String objectClassname,
        String objectNumber, Property property)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);
        objectClassname = convertToUTF(objectClassname);
        objectNumber = convertToUTF(objectNumber);

        ObjectResources objectresources = new ObjectResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            objectresources.setAuthenticaion(username, password);
        }
        try {
            return objectresources.addObjectProperty(objectClassname, objectNumber, property);
        } catch (RestConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "error";
    }
}
