package org.xwiki.android.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.util.Log;

public class Requests
{

    private String URLprefix;

    public Requests(String URLprefix)
    {
        this.URLprefix = URLprefix;
    }

    // Method for search
    public String requestSearch(String wikiName, String keyword)
    {

        wikiName = convertToUTF(wikiName);
        keyword = convertToUTF(keyword);

        Search search = new Search(URLprefix);
        return search.decodeSearchResponse(search.doWikiSearch(wikiName, keyword));
    }

    public String requestSearch(String wikiName, String spaceName, String keyword)
    {

        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        keyword = convertToUTF(keyword);

        Search search = new Search(URLprefix);
        return search.decodeSearchResponse(search.doSpacesSearch(wikiName, spaceName, keyword));
    }

    // Method for retrieving Wikis
    public String requestWiki()
    {

        WikiResources wikiresources = new WikiResources(URLprefix);
        return wikiresources.decodeWikiResponse(wikiresources.getWikis());
    }

    // Method for retrieving Spaces
    public String requestSpaces(String wikiName)
    {
        wikiName = convertToUTF(wikiName);
        SpaceResources spacesresources = new SpaceResources(URLprefix, wikiName);
        return spacesresources.decodeSpaceResponse(spacesresources.getSpaces());
    }

    // Method for retrieving Spaces
    public String requestAllPages(String wikiName, String spaceName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        return pagesresources.decodeAllPagesResponse(pagesresources.getAllPages());
    }

    // Method for requesting pages
    public String requestPage(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        return pagesresources.decodePageResponse(pagesresources.getPage(pageName), "id");
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

    // Method for getting page history
    public String requestPageHistory(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        HistoryResources historyresources = new HistoryResources(URLprefix, wikiName, spaceName, pageName);
        return historyresources.getPageHistory();
    }

    // Method for getting page history
    public String requestPageInVersionHistory(String wikiName, String spaceName, String pageName, String version)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);
        return pagesresources.decodePageResponse(pagesresources.getPageHistoryOnVersion(pageName, version), "content");
    }

    // Method for getting page children
    public String requestPageChildren(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        PageResources pagesresources = new PageResources(URLprefix, wikiName, spaceName);

        return pagesresources.getPageChildren(pageName);

    }

    // Method for getting page tags
    public String requestPageTags(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        TagResources tagresources = new TagResources(URLprefix, wikiName, spaceName, pageName);

        return tagresources.getTags();
    }

    // Method for getting wiki tags
    public String requestWikiTags(String wikiName)
    {
        wikiName = convertToUTF(wikiName);

        TagResources tagresources = new TagResources(URLprefix, wikiName);

        return tagresources.getTags();
    }

    // Method for getting page tags
    public String requestPageComments(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        CommentResources commentresources = new CommentResources(URLprefix, wikiName, spaceName, pageName);

        return commentresources.getPageComments();
    }

    // Method for getting page tags with comment id
    public String requestPageComments(String wikiName, String spaceName, String pageName, String commentId)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        CommentResources commentresources = new CommentResources(URLprefix, wikiName, spaceName, pageName);

        return commentresources.getPageComments(commentId);
    }

    // Method for getting page tags with comment id
    public String requestPageCommentsInHistory(String wikiName, String spaceName, String pageName, String version)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        CommentResources commentresources = new CommentResources(URLprefix, wikiName, spaceName, pageName);

        return commentresources.getPageCommentsInHistory(version);
    }

    public String requestPageCommentsInHistory(String wikiName, String spaceName, String pageName, String version,
        String commentId)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        CommentResources commentresources = new CommentResources(URLprefix, wikiName, spaceName, pageName);

        return commentresources.getPageCommentsInHistory(version, commentId);
    }

    public String getAllPageAttachments(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);

        return attachmentresources.getAllPageAttachments();
    }

    public String getPageAttachment(String wikiName, String spaceName, String pageName, String attachmentName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);
        attachmentName = convertToUTF(attachmentName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);

        return attachmentresources.getPageAttachment(attachmentName);
    }

    public String getPageAttachmentsInHistory(String wikiName, String spaceName, String pageName, String pageVersion)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);

        return attachmentresources.getPageAttachmentsInHistory(pageVersion);
    }

    public String getPageAttachmentsInHistory(String wikiName, String spaceName, String pageName, String pageVersion,
        String attachmentName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);
        attachmentName = convertToUTF(attachmentName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);

        return attachmentresources.getPageAttachmentsInHistory(pageVersion, attachmentName);
    }

    public String getPageAttachmentsInAttachmentHistory(String wikiName, String spaceName, String pageName,
        String attachmentName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);
        attachmentName = convertToUTF(attachmentName);

        AttachmentResources attachmentresources = new AttachmentResources(URLprefix, wikiName, spaceName, pageName);

        return attachmentresources.getPageAttachmentsInAttachmentHistory(attachmentName);
    }

    public String getPageAttachmentsInAttachmentHistory(String wikiName, String spaceName, String pageName,
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
    public String getAllObjects(String wikiName, String spaceName, String pageName)
    {
        wikiName = convertToUTF(wikiName);
        spaceName = convertToUTF(spaceName);
        pageName = convertToUTF(pageName);

        ObjectResources objectresources = new ObjectResources(URLprefix, wikiName, spaceName, pageName);

        return objectresources.getAllObjects();
    }

    //Method for getting all classes in wiki
    public String getWikiClasses(String wikiName)
    {
        wikiName = convertToUTF(wikiName);

        ClassResources classresources = new ClassResources(URLprefix, wikiName);
        return classresources.getWikiClasses();
    }
    
  //Method for getting class in wiki
    public String getWikiClasses(String wikiName, String classname)
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
