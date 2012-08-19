package org.xwiki.android.rest;

/**
 * @author xwiki gsoc 2012 
 * RUNTIME EXCEPTION
 * This runtime exception is thrown for Rest API usage errors which cannot be corrected at
 *         runtime.
 */
public class IllegalRestUsageException extends RuntimeException
{
    public IllegalRestUsageException(String msg)
    {
        super(msg);
    }
}
