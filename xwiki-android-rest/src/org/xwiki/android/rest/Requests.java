package org.xwiki.android.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.xwiki.android.resources.Attachments;
import org.xwiki.android.resources.Comment;
import org.xwiki.android.resources.Comments;
import org.xwiki.android.resources.History;
import org.xwiki.android.resources.Objects;
import org.xwiki.android.resources.Page;
import org.xwiki.android.resources.Pages;
import org.xwiki.android.resources.SearchResults;
import org.xwiki.android.resources.Spaces;
import org.xwiki.android.resources.Tags;
import org.xwiki.android.resources.Wikis;

import android.util.Log;

public class Requests
{

    private String URLprefix;

    public Requests(String URLprefix)
    {
        this.URLprefix = URLprefix;
    }

    // Method for search [complete]
    public SearchResults requestSearch(String wikiName, String keyword)
    {

        wikiName = convertToUTF(wikiName);
        keyword = convertToUTF(keyword);

        Search search = new Search(URLprefix);
        return search.doWikiSearch(wikiName, keyword);
    }

    // search with spaces [complete]
    public SearchResults requestSearch(String wikiName, String spaceName, String keyword)
    {

        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        keyword = convertToUTF(keyword);

        Search search = new Search(URLprefix);
        return search.doSpacesSearch(wikiName, spaceName, keyword);
    }

    // Method for retrieving Wikis [complete]
    public Wikis requestWikis()
    {

        WikiResources wikiresources = new WikiResources(URLprefix);
        Wikis wikis = wikiresources.getWikis();
        return wikis;
    }

    // Method for retrieving Spaces [complete]
    public Spaces requestSpaces(String wikiName)
    {
        wikiName = convertToUTF(wikiName);
        SpaceResources spacesresources = new SpaceResources(URLprefix, wikiName);
        return spacesresources.getSpaces();
    }

    // Method for retrieving Pages [complete]
    public Pages requestAllPages(String wikiName, String spaceName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        return pagesresources.getAllPages();
    }

    // Method for requesting page [complete but not working due to calendar]
    public Page requestPage(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        return pagesresources.getPage(pageName);
    }

    // Method for deleting pages
    public String deletePage(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        return pagesresources.deletePage(pageName);
    }

    // Method for getting page history [complete but not working due to calendar]
    public History requestPageHistory(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        HistoryResources historyresources = new HistoryResources(URLprefix, wikiName, spaceName, pageName);
        return historyresources.getPageHistory();
    }

    // Method for getting page history [complete but not working due to calendar]
    public Page requestPageInVersionHistory(String wikiName, String spaceName, String pageName, String version)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        return pagesresources.getPageHistoryOnVersion(pageName, version);
    }

    // Method for getting page children [complete]
    public Pages requestPageChildren(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);

        return pagesresources.getPageChildren(pageName);

    }

    // Method for getting page tags [complete]
    public Tags requestPageTags(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        TagResources tagresources = new TagResources(URLprefix, wikiName, spaceName, pageName);

        return tagresources.getTags();
    }

    // Method for getting wiki tags [complete]
    public Tags requestWikiTags(String wikiName)
    {
        wikiName = convertToUTF(wikiName);

        TagResources tagresources = new TagResources(URLprefix, wikiName);

        return tagresources.getTags();
    }

    // Method for getting page comments [complete but not working due to calendar]
    public Comments requestPageComments(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        CommentResources commentresources = new CommentResources(URLprefix, wikiName, spaceName, pageName);

        return commentresources.getPageComments();
    }

    // Method for getting page comments with comment id [complete but not working due to calendar]
    public Comment requestPageComments(String wikiName, String spaceName, String pageName, String commentId)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        CommentResources commentresources = new CommentResources(URLprefix, wikiName, spaceName, pageName);

        return commentresources.getPageComments(commentId);
    }

    // Method for getting page comments with comment id [complete but not working due to calendar]
    public Comments requestPageCommentsInHistory(String wikiName, String spaceName, String pageName, String version)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        CommentResources commentresources = new CommentResources(URLprefix, wikiName, spaceName, pageName);

        return commentresources.getPageCommentsInHistory(version);
    }

    // Method for getting page comments with version and comment id [complete but not working due to calendar]
    public Comments requestPageCommentsInHistory(String wikiName, String spaceName, String pageName, String version,
        String commentId)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        CommentResources commentresources = new CommentResources(URLprefix, wikiName, spaceName, pageName);

        return commentresources.getPageCommentsInHistory(version, commentId);
    }

    // Mehtod for getting all attachements in a page [complete but not working due to calendar]
    public Attachments requestAllPageAttachments(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);

        return attachmentresources.getAllPageAttachments();
    }

    // Method for getting attachment by name in a page [should return attachment bytes]
    public String requestPageAttachment(String wikiName, String spaceName, String pageName, String attachmentName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);
        attachmentName = convertToUTF(attachmentName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);

        return attachmentresources.getPageAttachment(attachmentName);
    }

    // Method for getting attachments in a specific page version [complete but not working due to calendar]
    public Attachments requestPageAttachmentsInHistory(String wikiName, String spaceName, String pageName,
        String pageVersion)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);

        return attachmentresources.getPageAttachmentsInHistory(pageVersion);
    }

    // Method for getting attachment by name in a specific page version [should return attachment bytes]
    public String requestPageAttachmentsInHistory(String wikiName, String spaceName, String pageName,
        String pageVersion, String attachmentName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);
        attachmentName = convertToUTF(attachmentName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);

        return attachmentresources.getPageAttachmentsInHistory(pageVersion, attachmentName);
    }

    //Method for getting attachment history of a attachment [complete but not working due to calendar]
    public Attachments requestPageAttachmentsInAttachmentHistory(String wikiName, String spaceName, String pageName,
        String attachmentName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);
        attachmentName = convertToUTF(attachmentName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);

        return attachmentresources.getPageAttachmentsInAttachmentHistory(attachmentName);
    }

 // Method for getting attachment by name in a specific attachment version [should return attachment bytes]
    public String requestPageAttachmentsInAttachmentHistory(String wikiName, String spaceName, String pageName,
        String attachmentName, String attachmentVersion)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);
        attachmentName = convertToUTF(attachmentName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);

        return attachmentresources.getPageAttachmentsInAttachmentHistory(attachmentName, attachmentVersion);
    }

    // Method for getting all objects in a page
    public Objects requestAllObjects(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        ObjectResources objectresources = new ObjectResources(URLprefix, wikiName, spaceName, pageName);

        return objectresources.getAllObjects();
    }

    // Method for getting all classes in wiki
    public String requestWikiClasses(String wikiName)
    {
        wikiName = convertToUTF(wikiName);

        ClassResources classresources = new ClassResources(URLprefix, wikiName);
        return classresources.getWikiClasses();
    }

    // Method for getting class in wiki
    public String requestWikiClasses(String wikiName, String classname)
    {
        wikiName = convertToUTF(wikiName);
        classname = convertToUTF(classname);

        ClassResources classresources = new ClassResources(URLprefix, wikiName);
        return classresources.getWikiClasses(classname);
    }

    // Method to convert String to the UTF
    public String convertToUTF(String keyword)
    {

        // Convert keyword to UTF
        try {
            keyword = URLEncoder.encode(keyword, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            Log.d("Error", "Unsupported keyword is found");
            keyword = "error";
            e.printStackTrace();
        }

        return keyword;
    }
}
