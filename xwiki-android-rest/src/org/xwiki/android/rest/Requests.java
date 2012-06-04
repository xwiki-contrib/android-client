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

    /**
     * Set the user credentials for accession
     * 
     * @param username username of the user account in XWiki
     * @param password password of the user account in XWiki
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

    /**
     * @param wikiName name of the Wiki
     * @param keyword text to search
     * @return Search results of the searched keyword within Wiki contents
     */
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

    /**
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param keyword text to search
     * @return Search results of the searched keyword in provided space
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
        return search.doSpacesSearch(wikiName, spaceName, keyword);
    }

    /**
     * @return list of wikis in the provided XWiki URL domain
     */
    public Wikis requestWikis()
    {

        WikiResources wikiresources = new WikiResources(URLprefix);
        if (isAuthenticated) {
            wikiresources.setAuthenticaion(username, password);
        }
        Wikis wikis = wikiresources.getWikis();
        return wikis;
    }

    /**
     * @param wikiName name of the wiki
     * @return list of spaces in the provided Wiki
     */
    public Spaces requestSpaces(String wikiName)
    {
        wikiName = convertToUTF(wikiName);
        SpaceResources spacesresources = new SpaceResources(URLprefix, wikiName);
        if (isAuthenticated) {
            spacesresources.setAuthenticaion(username, password);
        }
        return spacesresources.getSpaces();
    }

    /**
     * @param wikiName name of the wiki
     * @param spaceName name of the space
     * @return list of all the pages in specific Wiki and Space
     */
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

    /**
     * @param wikiName name of the wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @return Page object according to the field values
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
        return pagesresources.getPage(pageName);
    }

    /**
     * Add page to the server according to the provided field values
     * 
     * @param wikiName name of the wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @param page Page object which is to be added
     * @return status of the HTTP response
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
        return pagesresources.addPage(page);
    }

    /**
     * Delete page in the server
     * 
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @return status of the HTTP response
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
        return pagesresources.deletePage(pageName);
    }

    /**
     * Delete page translation
     * 
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @param language specific language to delete
     * @return status of the HTTP response
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
        return pagesresources.deletePageTranslation(pageName, language);
    }

    /**
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @return history of the page according to the field values
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
        return historyresources.getPageHistory();
    }

    /**
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @param version version of the page
     * @return Page of the specified version
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
        return pagesresources.getPageHistoryOnVersion(pageName, version);
    }

    /**
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @return Children pages of the specific page
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
        return pagesresources.getPageChildren(pageName);

    }

    /**
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @return Tags of the page
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
        return tagresources.getTags();
    }

    /**
     * Adds page tag to the provided page
     * 
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @param tag tag to be added
     * @return status of the HTTP response
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
        return tagresources.addTag(tag);

    }

    /**
     * @param wikiName name of the Wiki
     * @return all the tags used in the wiki
     */
    public Tags requestWikiTags(String wikiName)
    {
        wikiName = convertToUTF(wikiName);

        TagResources tagresources = new TagResources(URLprefix, wikiName);
        if (isAuthenticated) {
            tagresources.setAuthenticaion(username, password);
        }
        return tagresources.getTags();
    }

    /**
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @return all the comments in the specific page
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
        return commentresources.getPageComments();
    }

    /**
     * Add comment to the page
     * 
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @param comment Comment object to be added
     * @return status of the HTTP response
     */
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

    /**
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @param commentId ID of the comment
     * @return page comment according to the comment ID
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
        return commentresources.getPageComment(commentId);
    }

    /**
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @param version version of the page
     * @return All the comments of the page specific version
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
        return commentresources.getPageCommentsInHistory(version);
    }

    /**
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @param version version of the page
     * @param commentId ID of the comment
     * @return comments Comments of specific comment ID in specific page history version
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
        return commentresources.getPageCommentsInHistory(version, commentId);
    }

    /**
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @return list all the attachments in a specific page
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

        return attachmentresources.getAllPageAttachments();
    }

    /**
     * Gets a stream of data of a attachment
     * 
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @param attachmentName filename of the attachment.
     * @return InputStream of the attachment raw data
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
        return attachmentresources.getPageAttachment(attachmentName);
    }

    /**
     * Add attachment to a page
     * 
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @param filePath absolute path to the attachment in internal/external memory of the device
     * @param attachmentName filename of the attachment
     * @return status of the HTTP response
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
        return attachmentresources.putPageAttachment(filePath, attachmentName);
    }

    /**
     * Delete the page attachment by its name
     * 
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @param attachmentName name of the attachment
     * @return status of the HTTP response
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
        return attachmentresources.deletePageAttachment(attachmentName);
    }

    /**
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @param pageVersion version of the page
     * @return list of all attachments in the specified page history version
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
        return attachmentresources.getPageAttachmentsInHistory(pageVersion);
    }

    /**
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @param pageVersion version of the page
     * @param attachmentName filename of the attachment
     * @return InputStream of attachment raw data in a specific page history version
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
        return attachmentresources.getPageAttachmentsInHistory(pageVersion, attachmentName);
    }

    /**
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @param attachmentName filename of the attachment
     * @return list of attachment version history of a specific attachment
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
        return attachmentresources.getPageAttachmentsInAttachmentHistory(attachmentName);
    }

    /**
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @param attachmentName filename of the attachment
     * @param attachmentVersion version of the attachment
     * @return Stream of attachment raw data in a specific attachment version of a specified page
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
        return attachmentresources.getPageAttachmentsInAttachmentHistory(attachmentName, attachmentVersion);
    }

    /**
     * @param wikiName name of the Wiki
     * @return lists all the classes in the specified Wiki
     */
    public Classes requestWikiClasses(String wikiName)
    {
        wikiName = convertToUTF(wikiName);

        ClassResources classresources = new ClassResources(URLprefix, wikiName);
        if (isAuthenticated) {
            classresources.setAuthenticaion(username, password);
        }
        return classresources.getWikiClasses();
    }

    /**
     * @param wikiName name of the Wiki
     * @param classname name of the class
     * @return Class of the provided classname in the specified Wiki
     */
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

    /**
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @return list all the Translations of a specific page
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
        return translationresources.getAllTranslations();
    }

    /**
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @param language language name of the page translations
     * @return Page of the requested language
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
        return pageresources.getPageTranslation(pageName, language);
    }

    /**
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @param language language of the page
     * @return Page history versions in a given language
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
        return historyresources.getPageHistory(language);
    }

    /**
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @param language language of the page
     * @param version version of the page
     * @return Translated page of the given history version
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
        return pageresources.getPageTranslation(pageName, language, version);
    }

    /**
     * Adds provided object to the given page
     * 
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @param object Object to be added to page
     * @return status of the HTTP response
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
        return objectresources.addObject(object);
    }

    /**
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @return All the objects of the specified page
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
        return objectresources.getAllObjects();
    }

    /**
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @param objectClassname class name of the objects
     * @return All the objects which have given class name of the specified page
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
        return objectresources.getObjectsInClassname(objectClassname);
    }

    /**
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @param objectClassname class name of the objects
     * @param objectNumber number of the object in the specific class
     * @return Object of the given class name and object number of the specified page
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
        return objectresources.getObject(objectClassname, objectNumber);
    }

    /**
     * Deletes the given object. Object can be selected using class name and the object number
     * 
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @param objectClassname class name of the objects
     * @param objectNumber number of the object in the specified class
     * @return status of the HTTP response of the delete operation
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
        return objectresources.deleteObject(objectClassname, objectNumber);
    }

    /** 
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @param objectClassname class name of the objects
     * @param objectNumber number of the object in the specified class
     * @return lists all the properties of a specific object
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
        return objectresources.getObjectProperties(objectClassname, objectNumber);
    }

    /**
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @param objectClassname class name of objects
     * @param objectNumber number of the object in the page
     * @param propertyName name of the property in the object
     * @return Property out of list of properties in a given object of a page
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
        return objectresources.getObjectProperty(objectClassname, objectNumber, propertyName);
    }

    /**
     * Adds the property to the object of a page
     * 
     * @param wikiName name of the Wiki
     * @param spaceName name of the space
     * @param pageName name of the page
     * @param objectClassname class name of the objects
     * @param objectNumber number of the object
     * @param property Property object to be added to the object in a page
     * @return status of the HTTP response
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
        return objectresources.addObjectProperty(objectClassname, objectNumber, property);
    }
}
