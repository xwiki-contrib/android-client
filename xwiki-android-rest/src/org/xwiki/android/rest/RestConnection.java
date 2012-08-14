package org.xwiki.android.rest;

public interface RestConnection
{
    XWikiAPI getBaseAPI();
    boolean isClosed();
    void close();
}
