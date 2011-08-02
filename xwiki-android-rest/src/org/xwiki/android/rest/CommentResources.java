package org.xwiki.android.rest;

import java.io.StringWriter;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xwiki.android.resources.Comment;
import org.xwiki.android.resources.Comments;


public class CommentResources extends HttpConnector
{
    private final String PAGE_URL_PREFIX = "/xwiki/rest/wikis/";

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
                + "/comments";

        return buildComments(super.getResponse(Uri));
    }

    public Comment getPageComment(String commentId)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/comments/" + commentId;

        return buildComment(super.getResponse(Uri));
    }

    public Comments getPageCommentsInHistory(String version)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/history/" + version + "/comments";

        return buildComments(super.getResponse(Uri));
    }

    public Comments getPageCommentsInHistory(String version, String commentId)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/history/" + version + "/comments/" + commentId;

        return buildComments(super.getResponse(Uri));
    }

    public String addPageComment(Comment comment)
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/comments";

        return super.postRequest(Uri, buildXmlComment(comment));
    }

  
    // decode xml content to Comments element
    private Comments buildComments(String content)
    {
        Comments comments = new Comments();

        Serializer serializer = new Persister();

        try {
            comments = serializer.read(Comments.class, content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return comments;
    }

    // build xml from Comments object
    private String buildXmlComments(Comments comments)
    {
        Serializer serializer = new Persister();

        StringWriter result = new StringWriter();

        try {
            serializer.write(comments, result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result.toString();
    }

    // decode xml content to Comment element
    private Comment buildComment(String content)
    {
        Comment comment = new Comment();

        Serializer serializer = new Persister();

        try {
            comment = serializer.read(Comment.class, content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return comment;
    }

    // build xml from Comment object
    private String buildXmlComment(Comment comment)
    {
        Serializer serializer = new Persister();

        StringWriter result = new StringWriter();

        try {
            serializer.write(comment, result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result.toString();
    }

}
