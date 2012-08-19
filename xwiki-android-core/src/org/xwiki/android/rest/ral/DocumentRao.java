package org.xwiki.android.rest.ral;

import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.reference.DocumentReference;
import org.xwiki.android.rest.reference.EntityReference;
import org.xwiki.android.xmodel.entity.Document;

public interface DocumentRao extends Rao<Document>
{
    

    /**
     * semantics: 
     * XOMyClass o1,o2,o3;
     * doc.addObject(o1)
     * doc.setObject("XOMyClass/0",o2)
     * doc.setObject("XOMyClass/2",o3)
     * will add o2 , o1, o3 in the create.
     * It tries to keep the order.
     * 
     * !please mind that if you have
     * XOMyClass o1;
     * doc.add(o1);
     * doc.set("XOMyClass/0",o1)
     * the create method adds the same object twice to the remote page.
     * @param doc 
     * @return created document extracted from server.
     */
    @Override
    public Document create(Document doc) throws RestConnectionException ,RaoException;
    
    /**
     * check wether the doc exists on server
     * @param dref
     * @return
     */
    public boolean exists(DocumentReference dref) throws RestConnectionException,RaoException;;
    
    /**
     * default semantics
     * 
     * @param dref
     * @return
     * @throws RestConnectionException
     * @throws RaoException
     */
    Document retreive(DocumentReference dref) throws RestConnectionException, RaoException;

    Document retreive(DocumentReference dref, FetchConfig lazyConfig) throws RestConnectionException, RaoException;
    
    Document retreive(Document doc, FetchConfig lazyConfig);

    void delete(DocumentReference dRef) throws RestConnectionException, RaoException;
}
