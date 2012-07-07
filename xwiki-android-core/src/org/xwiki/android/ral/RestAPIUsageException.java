package org.xwiki.android.ral;

/**
 * @author xwiki gsoc 2012 This runtime exception is thrown for Rest API usage errors which cannot be corrected at
 *         runtime.
 */
public class RestAPIUsageException extends RuntimeException
{
    public RestAPIUsageException(String msg)
    {
        super(msg);
    }
}
