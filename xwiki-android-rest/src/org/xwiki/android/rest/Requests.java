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
 * "request" will return objects XWiki Android REST model. Currently using xwiki-rest-model-simplexml. Method names
 * starting from "add" will add XWiki Android REST model objects to the XWiki Server.
 */
public class Requests implements XWikiRestfulAPI
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
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#setAuthentication(java.lang.String, java.lang.String)
	 */
    @Override
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
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestSearch(java.lang.String, java.lang.String)
	 */
    @Override
	public SearchResults requestSearch(String wikiName, String keyword)
    {

        wikiName = convertToUTF(wikiName);
        keyword = convertToUTF(keyword);

        Search search = new Search(URLprefix);

        if (isAuthenticated) {
            search.setAuthenticaion(username, password);
        }

        return search.doWikiSearch(wikiName, keyword);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestSearch(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
	public SearchResults requestSearch(String wikiName, String spaceName, String keyword)
    {

        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        keyword = convertToUTF(keyword);

        Search search = new Search(URLprefix);
        if (isAuthenticated) {
            search.setAuthenticaion(username, password);
        }
        return search.doSpacesSearch(wikiName, spaceName, keyword);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestWikis()
	 */
    @Override
	public Wikis requestWikis()
    {

        WikiResources wikiresources = new WikiResources(URLprefix);
        if (isAuthenticated) {
            wikiresources.setAuthenticaion(username, password);
        }
        Wikis wikis = wikiresources.getWikis();
        return wikis;
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestSpaces(java.lang.String)
	 */
    @Override
	public Spaces requestSpaces(String wikiName)
    {
        wikiName = convertToUTF(wikiName);
        SpaceResources spacesresources = new SpaceResources(URLprefix, wikiName);
        if (isAuthenticated) {
            spacesresources.setAuthenticaion(username, password);
        }
        return spacesresources.getSpaces();
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestAllPages(java.lang.String, java.lang.String)
	 */
    @Override
	public Pages requestAllPages(String wikiName, String spaceName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pagesresources.setAuthenticaion(username, password);
        }
        return pagesresources.getAllPages();
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestPage(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
	public Page requestPage(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pagesresources.setAuthenticaion(username, password);
        }
        return pagesresources.getPage(pageName);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#addPage(java.lang.String, java.lang.String, java.lang.String, org.xwiki.android.resources.Page)
	 */
    @Override
	public String addPage(String wikiName, String spaceName, String pageName, Page page)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pagesresources.setAuthenticaion(username, password);
        }
        return pagesresources.addPage(page);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#deletePage(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
	public String deletePage(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pagesresources.setAuthenticaion(username, password);
        }
        return pagesresources.deletePage(pageName);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#deletePage(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
	public String deletePage(String wikiName, String spaceName, String pageName, String language)

    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pagesresources.setAuthenticaion(username, password);
        }
        return pagesresources.deletePageTranslation(pageName, language);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestPageHistory(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
	public History requestPageHistory(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        HistoryResources historyresources = new HistoryResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            historyresources.setAuthenticaion(username, password);
        }
        return historyresources.getPageHistory();
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestPageInVersionHistory(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
	public Page requestPageInVersionHistory(String wikiName, String spaceName, String pageName, String version)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pagesresources.setAuthenticaion(username, password);
        }
        return pagesresources.getPageHistoryOnVersion(pageName, version);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestPageChildren(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
	public Pages requestPageChildren(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pagesresources.setAuthenticaion(username, password);
        }
        return pagesresources.getPageChildren(pageName);

    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestPageTags(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
	public Tags requestPageTags(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        TagResources tagresources = new TagResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            tagresources.setAuthenticaion(username, password);
        }
        return tagresources.getTags();
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#addPageTag(java.lang.String, java.lang.String, java.lang.String, org.xwiki.android.resources.Tag)
	 */
    @Override
	public String addPageTag(String wikiName, String spaceName, String pageName, Tag tag)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        TagResources tagresources = new TagResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            tagresources.setAuthenticaion(username, password);
        }
        return tagresources.addTag(tag);

    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestWikiTags(java.lang.String)
	 */
    @Override
	public Tags requestWikiTags(String wikiName)
    {
        wikiName = convertToUTF(wikiName);

        TagResources tagresources = new TagResources(URLprefix, wikiName);
        if (isAuthenticated) {
            tagresources.setAuthenticaion(username, password);
        }
        return tagresources.getTags();
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestPageComments(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
	public Comments requestPageComments(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        CommentResources commentresources = new CommentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            commentresources.setAuthenticaion(username, password);
        }
        return commentresources.getPageComments();
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#addPageComment(java.lang.String, java.lang.String, java.lang.String, org.xwiki.android.resources.Comment)
	 */
    @Override
	public String addPageComment(String wikiName, String spaceName, String pageName, Comment comment)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        CommentResources commentresources = new CommentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            commentresources.setAuthenticaion(username, password);
            return commentresources.addPageComment(comment);
        } else {
            return "Autherization details are not provided";
        }
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestPageComments(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
	public Comment requestPageComments(String wikiName, String spaceName, String pageName, String commentId)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        CommentResources commentresources = new CommentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            commentresources.setAuthenticaion(username, password);
        }
        return commentresources.getPageComment(commentId);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestPageCommentsInHistory(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
	public Comments requestPageCommentsInHistory(String wikiName, String spaceName, String pageName, String version)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        CommentResources commentresources = new CommentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            commentresources.setAuthenticaion(username, password);
        }
        return commentresources.getPageCommentsInHistory(version);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestPageCommentsInHistory(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
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
        return commentresources.getPageCommentsInHistory(version, commentId);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestAllPageAttachments(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
	public Attachments requestAllPageAttachments(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            attachmentresources.setAuthenticaion(username, password);
        }

        return attachmentresources.getAllPageAttachments();
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestPageAttachment(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
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
        return attachmentresources.getPageAttachment(attachmentName);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#addPageAttachment(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
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
        return attachmentresources.putPageAttachment(filePath, attachmentName);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#deletePageAttachment(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
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
        return attachmentresources.deletePageAttachment(attachmentName);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestPageAttachmentsInHistory(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
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
        return attachmentresources.getPageAttachmentsInHistory(pageVersion);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestPageAttachmentsInHistory(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
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
        return attachmentresources.getPageAttachmentsInHistory(pageVersion, attachmentName);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestPageAttachmentsInAttachmentHistory(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
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
        return attachmentresources.getPageAttachmentsInAttachmentHistory(attachmentName);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestPageAttachmentsInAttachmentHistory(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
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
        return attachmentresources.getPageAttachmentsInAttachmentHistory(attachmentName, attachmentVersion);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestWikiClasses(java.lang.String)
	 */
    @Override
	public Classes requestWikiClasses(String wikiName)
    {
        wikiName = convertToUTF(wikiName);

        ClassResources classresources = new ClassResources(URLprefix, wikiName);
        if (isAuthenticated) {
            classresources.setAuthenticaion(username, password);
        }
        return classresources.getWikiClasses();
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestWikiClasses(java.lang.String, java.lang.String)
	 */
    @Override
	public Class requestWikiClasses(String wikiName, String classname)
    {
        wikiName = convertToUTF(wikiName);
        classname = convertToUTF(classname);

        ClassResources classresources = new ClassResources(URLprefix, wikiName);
        if (isAuthenticated) {
            classresources.setAuthenticaion(username, password);
        }
        return classresources.getWikiClasses(classname);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestAllPageTranslations(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
	public Translations requestAllPageTranslations(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        TranslationResources translationresources = new TranslationResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            translationresources.setAuthenticaion(username, password);
        }
        return translationresources.getAllTranslations();
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestPageTranslation(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
	public Page requestPageTranslation(String wikiName, String spaceName, String pageName, String language)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        PageResources pageresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pageresources.setAuthenticaion(username, password);
        }
        return pageresources.getPageTranslation(pageName, language);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestPageHistoryInLanguage(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
	public History requestPageHistoryInLanguage(String wikiName, String spaceName, String pageName, String language)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        HistoryResources historyresources = new HistoryResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            historyresources.setAuthenticaion(username, password);
        }
        return historyresources.getPageHistory(language);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestPageTranslationInHistory(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
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
        return pageresources.getPageTranslation(pageName, language, version);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#addObject(java.lang.String, java.lang.String, java.lang.String, org.xwiki.android.resources.Object)
	 */
    @Override
	public String addObject(String wikiName, String spaceName, String pageName, Object object)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        ObjectResources objectresources = new ObjectResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            objectresources.setAuthenticaion(username, password);
        }
        return objectresources.addObject(object);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestAllObjects(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
	public Objects requestAllObjects(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        ObjectResources objectresources = new ObjectResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            objectresources.setAuthenticaion(username, password);
        }
        return objectresources.getAllObjects();
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestObjectsInClass(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
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
        return objectresources.getObjectsInClassname(objectClassname);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestObject(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
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
        return objectresources.getObject(objectClassname, objectNumber);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#deleteObject(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
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
        return objectresources.deleteObject(objectClassname, objectNumber);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestObjectAllProperties(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
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
        return objectresources.getObjectProperties(objectClassname, objectNumber);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#requestObjectProperty(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
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
        return objectresources.getObjectProperty(objectClassname, objectNumber, propertyName);
    }

    /* (non-Javadoc)
	 * @see org.xwiki.android.rest.XWikiRestfulAPI#addProperty(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, org.xwiki.android.resources.Property)
	 */
    @Override
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
        return objectresources.addObjectProperty(objectClassname, objectNumber, property);
    }
}
