package org.xwiki.android.rest;

import org.xwiki.android.resources.Comment;
import org.xwiki.android.resources.Comments;

import com.google.gson.Gson;

public class CommentResources extends HttpConnector
{
    private final String PAGE_URL_PREFIX = "/xwiki/rest/wikis/";

    private final String JSON_URL_SUFFIX = "?media=json";

    private String URLprefix;

    private String wikiName;

    private String spaceName;

    private String pageName;

    public CommentResources(String URLprefix, String wikiName, String spaceName, String pageName)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
        this.spaceName = spaceName;
        this.pageName = pageName;
    }

    public Comments getPageComments()
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/comments" + JSON_URL_SUFFIX;

        return decodeComments(super.getResponse(Uri));
    }

    public Comment getPageComments(String commentId)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/comments/" + commentId + JSON_URL_SUFFIX;

        return decodeComment(super.getResponse(Uri));
    }

    public Comments getPageCommentsInHistory(String version)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/history/" + version + "/comments" + JSON_URL_SUFFIX;

        return decodeComments(super.getResponse(Uri));
    }

    public Comments getPageCommentsInHistory(String version, String commentId)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/history/" + version + "/comments/" + commentId + JSON_URL_SUFFIX;

        return decodeComments(super.getResponse(Uri));
    }

    // decode json content to Page element
    private Comments decodeComments(String content)
    {
        Gson gson = new Gson();

        Comments comments = new Comments();
        comments = gson.fromJson(content, Comments.class);
        return comments;
    }
    
 // decode json content to Page element
    private Comment decodeComment(String content)
    {
        Gson gson = new Gson();

        Comment comment = new Comment();
        comment = gson.fromJson(content, Comment.class);
        return comment;
    }
}
