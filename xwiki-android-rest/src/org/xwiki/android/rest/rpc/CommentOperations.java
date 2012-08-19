package org.xwiki.android.rest.rpc;

import org.xwiki.android.resources.Comment;
import org.xwiki.android.resources.Comments;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;

public interface CommentOperations
{

    /**
     * @return Comments object of the page
     * @throws RestConnectionException
     * @throws RestException
     */
    public abstract Comments getPageComments() throws RestConnectionException, RestException;

    public abstract boolean exists(int commentId) throws RestConnectionException, RestException;

    /**
     * @param commentId ID of the comment in the page
     * @return comment of a page selected using comment ID
     * @throws RestConnectionException
     * @throws RestException
     */
    public abstract Comment getPageComment(int commentId) throws RestConnectionException, RestException;

    /**
     * @param version version of the page
     * @return list of comments as a Comments object in a specific page history version
     * @throws RestConnectionException
     * @throws RestException
     */
    public abstract Comments getPageCommentsInHistory(String version) throws RestConnectionException, RestException;

    /**
     * @param version version of the page
     * @param commentId ID of the comment in the page
     * @return Comment in a specific page history version
     * @throws RestConnectionException
     * @throws RestException
     */
    public abstract Comments getPageCommentsInHistory(String version, String commentId) throws RestConnectionException,
        RestException;

    /**
     * Adds comment to the page
     * 
     * @param comment Comment object to be added
     * @return comment id.
     * @throws RestConnectionException
     * @throws RestException
     */
    public abstract int addPageComment(Comment comment) throws RestConnectionException, RestException;

    public abstract Comment addPageCommentForResource(Comment comment) throws RestConnectionException, RestException;

}
