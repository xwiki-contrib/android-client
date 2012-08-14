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

package org.xwiki.android.test.utils.xmlrpc;

import org.apache.http.util.EntityUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xwiki.android.resources.Comment;
import org.xwiki.android.resources.Comments;

import java.io.IOException;
import java.io.StringWriter;

/**
 * XWiki Android REST Comments related source. Can get the comments details/list as a Comment(s) objects of Simple-xml
 * object model
 */
public class CommentOperations
{
    /**
     * Path provided from XWiki RESTful API
     */
    private final String PAGE_URL_PREFIX = "/xwiki/rest/wikis/";

    /**
     * URL of the XWiki domain
     */
    private String URLprefix;

    /**
     * Name of Wiki for acquiring comments
     */
    private String wikiName;

    /**
     * Name of space for acquiring comments
     */
    private String spaceName;

    /**
     * Name of page for acquiring comments
     */
    private String pageName;
    private RestClient rpc;

    /**
     * @param URLprefix XWiki URl ex:"www.xwiki.org"
     * @param wikiName name of the wiki in UTF-8 format
     * @param spaceName name of the space in UTF-8 format
     * @param pageName name of the page in UTF-8 format
     * @param rpc
     */
    CommentOperations(String URLprefix, String wikiName, String spaceName, String pageName, RestClient rpc)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
        this.spaceName = spaceName;
        this.pageName = pageName;
        this.rpc=rpc;
    }

    /**
     * @return Comments object of the page
     * @throws IOException
     * @throws RestException
     */
    public Comments getPageComments() throws IOException, RestException
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/comments";
        return buildComments(EntityUtils.toString(rpc.getRequest(Uri).getEntity()));
    }

    public boolean exists(int commentId) throws IOException, RestException
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/comments/" + commentId;
        int code = rpc.headRequest(Uri);
        if (code == 200) {
            return true;
        }
        if (code == 404) {
            return false;
        } else {
            throw new RestException(code);
        }
    }

    /**
     * @param commentId ID of the comment in the page
     * @return comment of a page selected using comment ID
     * @throws IOException
     * @throws RestException
     */
    public Comment getPageComment(int commentId) throws IOException, RestException
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/comments/" + commentId;

        return buildComment(EntityUtils.toString(rpc.getRequest(Uri).getEntity()));
    }

    /**
     * @param version version of the page
     * @return list of comments as a Comments object in a specific page history version
     * @throws IOException
     * @throws RestException
     */
    public Comments getPageCommentsInHistory(String version) throws IOException, RestException
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/history/" + version + "/comments";

        return buildComments(EntityUtils.toString(rpc.getRequest(Uri).getEntity()));
    }

    /**
     * @param version version of the page
     * @param commentId ID of the comment in the page
     * @return Comment in a specific page history version
     * @throws IOException
     * @throws RestException
     */
    public Comments getPageCommentsInHistory(String version, String commentId) throws IOException,
        RestException
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/history/" + version + "/comments/" + commentId;

        return buildComments(EntityUtils.toString(rpc.getRequest(Uri).getEntity()));
    }

    /**
     * Adds comment to the page
     * 
     * @param comment Comment object to be added
     * @return status of the HTTP post request
     * @throws IOException
     * @throws RestException
     */
    public String addPageComment(Comment comment) throws IOException, RestException
    {
        String Uri =
            "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/spaces/" + spaceName + "/pages/" + pageName
                + "/comments";

        return rpc.postRequest(Uri, buildXmlComment(comment)).getStatusLine().toString();
    }

    /**
     * Parse xml into a Comments object
     * 
     * @param content XML content
     * @return Comments object deserialized from the xml content
     */
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

    /**
     * Generate XML content from the Comments object
     * 
     * @param comments Comments object to be serialized into XML
     * @return XML content of the provided Comments object
     */
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

    /**
     * Parse xml into a Comment object
     * 
     * @param content XML content
     * @return Comment object deserialized from the xml content
     */
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

    /**
     * Generate XML content from the Comment object
     * 
     * @param comment Comment object to be serialized into XML
     * @return XML content of the provided Comment object
     */
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
