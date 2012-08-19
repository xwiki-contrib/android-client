package org.xwiki.android.rest.rpc;

import org.xwiki.android.resources.Comment;
import org.xwiki.android.resources.Comments;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;


class _CommentOperations implements CommentOperations
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
    _CommentOperations(String URLprefix, String wikiName, String spaceName, String pageName, RestClient rpc)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
        this.spaceName = spaceName;
        this.pageName = pageName;
        this.rpc=rpc;
    }

    @Override
    public Comments getPageComments() throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean exists(int commentId) throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Comment getPageComment(int commentId) throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Comments getPageCommentsInHistory(String version) throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Comments getPageCommentsInHistory(String version, String commentId) throws RestConnectionException,
        RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int addPageComment(Comment comment) throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Comment addPageCommentForResource(Comment comment) throws RestConnectionException, RestException
    {
        // TODO Auto-generated method stub
        return null;
    }

}
