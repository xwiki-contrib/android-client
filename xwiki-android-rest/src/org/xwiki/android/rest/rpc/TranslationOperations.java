package org.xwiki.android.rest.rpc;

import org.xwiki.android.resources.Translations;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.RestException;

public interface TranslationOperations
{

    /**
     * @return list of all the translations are included in Translations object
     * @throws RestConnectionException 
     * @throws RestException 
     */
    public abstract Translations getAllTranslations() throws RestConnectionException, RestException;

}
