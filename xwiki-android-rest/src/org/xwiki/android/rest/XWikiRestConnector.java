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

public class XWikiRestConnector implements XWikiRestfulAPI
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
     * State of the gets has user authentication or not
     */
    private boolean isAuthenticated = false;

    /**
     * @param URLprefix URL of the XWiki domain
     */
    public XWikiRestConnector(String URLprefix)
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
    public XWikiRestConnector(String URLprefix, String username, String password)
    {
        this.URLprefix = URLprefix;
        this.username = username;
        this.password = password;
        isAuthenticated = true;
    }

    /*
     * (non-Javadoc)
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
     * Converts strings to UCS Transformation Format - 8-bit(UTF-8) for calling XWiki RESTful API Does URL encoding of
     * the keyword.
     * 
     * @param word text to convert
     * @return converted text
     */
    private String urlEncode(String word)
    {

        // Convert keyword to UTF-8
        try {
            word = URLEncoder.encode(word, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            Log.d("Error", "Unsupported keyword is found");
            word = "error in converting to UTF-8";
            e.printStackTrace();
        }

        return word;
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getSearch(java.lang.String, java.lang.String)
     */
    @Override
    public SearchResults searchInWiki(String wikiName, String keyword) throws RestConnectionException, RestException, RestException
    {

        wikiName = urlEncode(wikiName);
        keyword = urlEncode(keyword);

        Search search = new Search(URLprefix);

        if (isAuthenticated) {
            search.setAuthenticaion(username, password);
        }

        return search.doWikiSearch(wikiName, keyword);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getSearch(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public SearchResults searchInSpace(String wikiName, String spaceName, String keyword) throws RestConnectionException, RestException
    {

        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        keyword = urlEncode(keyword);

        Search search = new Search(URLprefix);
        if (isAuthenticated) {
            search.setAuthenticaion(username, password);
        }
        return search.doSpacesSearch(wikiName, spaceName, keyword);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getWikis()
     */
    @Override
    public Wikis getWikis() throws RestConnectionException, RestException
    {

        WikiResources wikiresources = new WikiResources(URLprefix);
        if (isAuthenticated) {
            wikiresources.setAuthenticaion(username, password);
        }
        Wikis wikis = wikiresources.getWikis();
        return wikis;
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getSpaces(java.lang.String)
     */
    @Override
    public Spaces getSpaces(String wikiName) throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        SpaceResources spacesresources = new SpaceResources(URLprefix, wikiName);
        if (isAuthenticated) {
            spacesresources.setAuthenticaion(username, password);
        }
        return spacesresources.getSpaces();
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getAllPages(java.lang.String, java.lang.String)
     */
    @Override
    public Pages getAllPages(String wikiName, String spaceName) throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pagesresources.setAuthenticaion(username, password);
        }
        return pagesresources.getAllPages();
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getPage(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Page getPage(String wikiName, String spaceName, String pageName) throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);

        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pagesresources.setAuthenticaion(username, password);
        }
        return pagesresources.getPage(pageName);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#addPage(java.lang.String, java.lang.String, java.lang.String,
     * org.xwiki.android.resources.Page)
     */
    @Override
    public String addPage(String wikiName, String spaceName, String pageName, Page page) throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);

        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pagesresources.setAuthenticaion(username, password);
        }
        return pagesresources.addPage(page);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#deletePage(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public String deletePage(String wikiName, String spaceName, String pageName) throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);

        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pagesresources.setAuthenticaion(username, password);
        }
        return pagesresources.deletePage(pageName);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#deletePage(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public String deletePage(String wikiName, String spaceName, String pageName, String language)
        throws RestConnectionException, RestException

    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);

        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pagesresources.setAuthenticaion(username, password);
        }
        return pagesresources.deletePageTranslation(pageName, language);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getPageHistory(java.lang.String, java.lang.String, java.lang.String)
     */

    @Override
    public History getPageHistory(String wikiName, String spaceName, String pageName) throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);

        HistoryResources historyresources = new HistoryResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            historyresources.setAuthenticaion(username, password);
        }
        return historyresources.getPageHistory();
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getPageInVersionHistory(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String)
     */

    @Override
    public Page getPageInVersionHistory(String wikiName, String spaceName, String pageName, String version)
        throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);

        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pagesresources.setAuthenticaion(username, password);
        }
        return pagesresources.getPageHistoryOnVersion(pageName, version);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getPageChildren(java.lang.String, java.lang.String, java.lang.String)
     */

    @Override
    public Pages getPageChildren(String wikiName, String spaceName, String pageName) throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);

        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pagesresources.setAuthenticaion(username, password);
        }
        return pagesresources.getPageChildren(pageName);

    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getPageTags(java.lang.String, java.lang.String, java.lang.String)
     */

    @Override
    public Tags getPageTags(String wikiName, String spaceName, String pageName) throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);

        TagResources tagresources = new TagResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            tagresources.setAuthenticaion(username, password);
        }
        return tagresources.getTags();
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#addPageTag(java.lang.String, java.lang.String, java.lang.String,
     * org.xwiki.android.resources.Tag)
     */

    @Override
    public String addPageTag(String wikiName, String spaceName, String pageName, Tag tag) throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);

        TagResources tagresources = new TagResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            tagresources.setAuthenticaion(username, password);
        }
        return tagresources.addTag(tag);

    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getWikiTags(java.lang.String)
     */

    @Override
    public Tags getWikiTags(String wikiName) throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);

        TagResources tagresources = new TagResources(URLprefix, wikiName);
        if (isAuthenticated) {
            tagresources.setAuthenticaion(username, password);
        }
        return tagresources.getTags();
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getPageComments(java.lang.String, java.lang.String, java.lang.String)
     */

    @Override
    public Comments getPageComments(String wikiName, String spaceName, String pageName) throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);

        CommentResources commentresources = new CommentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            commentresources.setAuthenticaion(username, password);
        }
        return commentresources.getPageComments();
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#addPageComment(java.lang.String, java.lang.String, java.lang.String,
     * org.xwiki.android.resources.Comment)
     */

    @Override
    public String addPageComment(String wikiName, String spaceName, String pageName, Comment comment)
        throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);

        CommentResources commentresources = new CommentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            commentresources.setAuthenticaion(username, password);
            return commentresources.addPageComment(comment);
        } else {
            return "Autherization details are not provided";
        }
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getPageComments(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String)
     */

    @Override
    public Comment getPageComments(String wikiName, String spaceName, String pageName, String commentId)
        throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);

        CommentResources commentresources = new CommentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            commentresources.setAuthenticaion(username, password);
        }
        return commentresources.getPageComment(commentId);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getPageCommentsInHistory(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String)
     */

    @Override
    public Comments getPageCommentsInHistory(String wikiName, String spaceName, String pageName, String version)
        throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);

        CommentResources commentresources = new CommentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            commentresources.setAuthenticaion(username, password);
        }
        return commentresources.getPageCommentsInHistory(version);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getPageCommentsInHistory(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String)
     */

    @Override
    public Comments getPageCommentsInHistory(String wikiName, String spaceName, String pageName, String version,
        String commentId) throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);

        CommentResources commentresources = new CommentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            commentresources.setAuthenticaion(username, password);
        }
        return commentresources.getPageCommentsInHistory(version, commentId);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getAllPageAttachments(java.lang.String, java.lang.String,
     * java.lang.String)
     */

    @Override
    public Attachments getAllPageAttachments(String wikiName, String spaceName, String pageName)
        throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            attachmentresources.setAuthenticaion(username, password);
        }

        return attachmentresources.getAllPageAttachments();
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getPageAttachment(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String)
     */

    @Override
    public InputStream getPageAttachment(String wikiName, String spaceName, String pageName, String attachmentName)
        throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);
        attachmentName = urlEncode(attachmentName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            attachmentresources.setAuthenticaion(username, password);
        }
        return attachmentresources.getPageAttachment(attachmentName);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#addPageAttachment(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String)
     */

    @Override
    public String addPageAttachment(String wikiName, String spaceName, String pageName, String filePath,
        String attachmentName) throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);
        attachmentName = urlEncode(attachmentName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            attachmentresources.setAuthenticaion(username, password);
        }
        return attachmentresources.putPageAttachment(filePath, attachmentName);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#deletePageAttachment(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String)
     */

    @Override
    public String deletePageAttachment(String wikiName, String spaceName, String pageName, String attachmentName)
        throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);
        attachmentName = urlEncode(attachmentName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            attachmentresources.setAuthenticaion(username, password);
        }
        return attachmentresources.deletePageAttachment(attachmentName);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getPageAttachmentsInHistory(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String)
     */

    @Override
    public Attachments getPageAttachmentsInHistory(String wikiName, String spaceName, String pageName,
        String pageVersion) throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            attachmentresources.setAuthenticaion(username, password);
        }
        return attachmentresources.getPageAttachmentsInHistory(pageVersion);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getPageAttachmentsInHistory(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String)
     */

    @Override
    public InputStream getPageAttachmentsInHistory(String wikiName, String spaceName, String pageName,
        String pageVersion, String attachmentName) throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);
        attachmentName = urlEncode(attachmentName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            attachmentresources.setAuthenticaion(username, password);
        }
        return attachmentresources.getPageAttachmentsInHistory(pageVersion, attachmentName);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getPageAttachmentsInAttachmentHistory(java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String)
     */

    @Override
    public Attachments getPageAttachmentsInAttachmentHistory(String wikiName, String spaceName, String pageName,
        String attachmentName) throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);
        attachmentName = urlEncode(attachmentName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            attachmentresources.setAuthenticaion(username, password);
        }
        return attachmentresources.getPageAttachmentsInAttachmentHistory(attachmentName);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getPageAttachmentsInAttachmentHistory(java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */

    @Override
    public InputStream getPageAttachmentsInAttachmentHistory(String wikiName, String spaceName, String pageName,
        String attachmentName, String attachmentVersion) throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);
        attachmentName = urlEncode(attachmentName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            attachmentresources.setAuthenticaion(username, password);
        }
        return attachmentresources.getPageAttachmentsInAttachmentHistory(attachmentName, attachmentVersion);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getWikiClasses(java.lang.String)
     */

    @Override
    public Classes getWikiClasses(String wikiName) throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);

        ClassResources classresources = new ClassResources(URLprefix, wikiName);
        if (isAuthenticated) {
            classresources.setAuthenticaion(username, password);
        }
        return classresources.getWikiClasses();
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getWikiClasses(java.lang.String, java.lang.String)
     */

    @Override
    public Class getWikiClasses(String wikiName, String classname) throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        classname = urlEncode(classname);

        ClassResources classresources = new ClassResources(URLprefix, wikiName);
        if (isAuthenticated) {
            classresources.setAuthenticaion(username, password);
        }
        return classresources.getWikiClasses(classname);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getAllPageTranslations(java.lang.String, java.lang.String,
     * java.lang.String)
     */

    @Override
    public Translations getAllPageTranslations(String wikiName, String spaceName, String pageName)
        throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);

        TranslationResources translationresources = new TranslationResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            translationresources.setAuthenticaion(username, password);
        }
        return translationresources.getAllTranslations();
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getPageTranslation(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String)
     */

    @Override
    public Page getPageTranslation(String wikiName, String spaceName, String pageName, String language)
        throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);

        PageResources pageresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pageresources.setAuthenticaion(username, password);
        }
        return pageresources.getPageTranslation(pageName, language);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getPageHistoryInLanguage(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String)
     */

    @Override
    public History getPageHistoryInLanguage(String wikiName, String spaceName, String pageName, String language)
        throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);

        HistoryResources historyresources = new HistoryResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            historyresources.setAuthenticaion(username, password);
        }
        return historyresources.getPageHistory(language);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getPageTranslationInHistory(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String)
     */

    @Override
    public Page getPageTranslationInHistory(String wikiName, String spaceName, String pageName, String language,
        String version) throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);

        PageResources pageresources = new PageResources(URLprefix, wikiName, spaceName);
        if (isAuthenticated) {
            pageresources.setAuthenticaion(username, password);
        }
        return pageresources.getPageTranslation(pageName, language, version);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#addObject(java.lang.String, java.lang.String, java.lang.String,
     * org.xwiki.android.resources.Object)
     */

    @Override
    public String addObject(String wikiName, String spaceName, String pageName, Object object)
        throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);

        ObjectResources objectresources = new ObjectResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            objectresources.setAuthenticaion(username, password);
        }
        return objectresources.addObject(object);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getAllObjects(java.lang.String, java.lang.String, java.lang.String)
     */

    @Override
    public Objects getAllObjects(String wikiName, String spaceName, String pageName) throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);

        ObjectResources objectresources = new ObjectResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            objectresources.setAuthenticaion(username, password);
        }
        return objectresources.getAllObjects();
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getObjectsInClass(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String)
     */

    @Override
    public Objects getObjectsInClass(String wikiName, String spaceName, String pageName, String objectClassname)
        throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);
        objectClassname = urlEncode(objectClassname);

        ObjectResources objectresources = new ObjectResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            objectresources.setAuthenticaion(username, password);
        }
        return objectresources.getObjectsInClassname(objectClassname);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getObject(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String)
     */

    @Override
    public Object getObject(String wikiName, String spaceName, String pageName, String objectClassname, int objectNumber)
        throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);
        objectClassname = urlEncode(objectClassname);
        String objectNumberStr = urlEncode("" + objectNumber);

        ObjectResources objectresources = new ObjectResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            objectresources.setAuthenticaion(username, password);
        }
        return objectresources.getObject(objectClassname, objectNumberStr);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#deleteObject(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String)
     */

    @Override
    public String deleteObject(String wikiName, String spaceName, String pageName, String objectClassname,
        String objectNumber) throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);
        objectClassname = urlEncode(objectClassname);
        objectNumber = urlEncode(objectNumber);

        ObjectResources objectresources = new ObjectResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            objectresources.setAuthenticaion(username, password);
        }
        return objectresources.deleteObject(objectClassname, objectNumber);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getObjectAllProperties(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String)
     */

    @Override
    public Properties getObjectAllProperties(String wikiName, String spaceName, String pageName,
        String objectClassname, String objectNumber) throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);
        objectClassname = urlEncode(objectClassname);
        objectNumber = urlEncode(objectNumber);

        ObjectResources objectresources = new ObjectResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            objectresources.setAuthenticaion(username, password);
        }
        return objectresources.getObjectProperties(objectClassname, objectNumber);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#getObjectProperty(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */

    @Override
    public Property getObjectProperty(String wikiName, String spaceName, String pageName, String objectClassname,
        String objectNumber, String propertyName) throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);
        objectClassname = urlEncode(objectClassname);
        objectNumber = urlEncode(objectNumber);
        propertyName = urlEncode(propertyName);

        ObjectResources objectresources = new ObjectResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            objectresources.setAuthenticaion(username, password);
        }
        return objectresources.getObjectProperty(objectClassname, objectNumber, propertyName);
    }

    /*
     * (non-Javadoc)
     * @see org.xwiki.android.rest.XWikiRestfulAPI#addProperty(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, org.xwiki.android.resources.Property)
     */

    @Override
    public String addObjectProperty(String wikiName, String spaceName, String pageName, String objectClassname,
        String objectNumber, Property property) throws RestConnectionException, RestException
    {
        wikiName = urlEncode(wikiName);
        spaceName = urlEncode(spaceName);
        pageName = urlEncode(pageName);
        objectClassname = urlEncode(objectClassname);
        objectNumber = urlEncode(objectNumber);

        ObjectResources objectresources = new ObjectResources(URLprefix, wikiName, spaceName, pageName);
        if (isAuthenticated) {
            objectresources.setAuthenticaion(username, password);
        }
        return objectresources.addObjectProperty(objectClassname, objectNumber, property);
    }
}
