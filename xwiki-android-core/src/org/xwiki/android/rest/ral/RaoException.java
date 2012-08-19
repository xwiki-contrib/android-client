package org.xwiki.android.rest.ral;

import org.xwiki.android.rest.RestException;

/**
 * @author xwiki gsoc 2012 For exceptional situations where client is doing some thing wrong due to lack of knowledge of
 *         the remote servers state.Ex: Call to create a document that already exsist in the remote server.
 */
//TODO: PLEASE SEE THE SUB EXCEPTION TYPES. Make Raos throw them at specific points.
public class RaoException extends Exception
{

    private RestException cause;
    private int code;

    public RaoException()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    public RaoException(String detailMessage, Throwable throwable)
    {
        super(detailMessage, throwable);
        // TODO Auto-generated constructor stub
    }

    public RaoException(String detailMessage, RestException rex)
    {
        super(detailMessage, rex);
        this.cause = rex;
        this.code = rex.getCode();
    }

    public RaoException(String detailMessage)
    {
        super(detailMessage);
        // TODO Auto-generated constructor stub
    }

    public RaoException(Throwable throwable)
    {
        super(throwable);
        // TODO Auto-generated constructor stub
    }

    public RaoException(RestException rex)
    {
        super(rex);
        this.cause = rex;
        this.code = rex.getCode();
    }

    public RestException getCause()
    {
        return cause;
    }

    public void setCause(RestException cause)
    {
        this.cause = cause;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public class DuplicateDocument extends RaoException
    {

    }

    public class DocumentNotFound extends RaoException
    {

    }

    /**
     * when updating a object in a document. Object not found thrown for set obj ops
     * 
     * @author xwiki gsoc 2012
     */
    public class ObjectNotFound extends RaoException
    {

    }

}
