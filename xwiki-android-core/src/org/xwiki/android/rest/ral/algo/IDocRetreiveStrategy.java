package org.xwiki.android.rest.ral.algo;

import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.ral.DocumentRao;
import org.xwiki.android.rest.ral.FetchConfig;
import org.xwiki.android.rest.ral.RaoException;
import org.xwiki.android.rest.reference.DocumentReference;
import org.xwiki.android.xmodel.entity.Document;

public interface IDocRetreiveStrategy
{
    Document retreive(Document resrc) throws RestConnectionException,RaoException;    
    /**
     * 
     * @param resrc
     * @param parts described in {@link DocumentRao}
     * @return
     */
    Document retreive(Document resrc, FetchConfig lazyConfig);  
    
    Document retreive(DocumentReference dref) throws RestConnectionException, RaoException;
    
    Document retreive(DocumentReference dref, FetchConfig lazyConfig);
    
    
}
