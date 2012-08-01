package org.xwiki.android.rest.ral;

/**
 * @author xwiki gsoc 2012 For exceptional situations where client is doing some thing wrong due to lack of knowledge of
 *         the remote servers state.Ex: Call to create a document that already exsist in the remote server.
 */
public class RaoException extends Exception
{

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

}
