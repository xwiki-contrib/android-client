package org.xwiki.android.rest.ral;

import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.reference.DocumentReference;
import org.xwiki.android.rest.reference.EntityReference;
import org.xwiki.android.xmodel.entity.Document;

public interface DocumentRao extends Rao<Document>
{
    //parts of a doc.
    int PAGE = 1;
    /**
     * all Objects except comment and tag objects.
     */
    int OBJECTS = 2;
    int COMMENTS = 4;
    int TAGS = 8;
    int ATTACHMENTS = 16;
    int COMMENT_OBJECTS=32;
    int TAG_OBJECT=64;
    int ALL = 2147483647;

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

    // TODO: SPECIFY default semantics ;-)
    /**
     * 
     * @param dref  ex: flags=OBJECTS+TAGS. Loads a Document object with XObjects and tags only.
     * @param flags
     * @return
     */
    Document retreive(DocumentReference dref, int flags);
    /**
     * 
     * @param dref
     * @param flags
     * @param objTypeArgs 
     *          ex: Blog.BlogPostClass:0:2 blog post objects from index 0 to 2. i.e 3 objs if available.
     * @return
     */
    Document retreive(DocumentReference dref, int flags, String... objTypeArgs);

    void delete(DocumentReference dRef) throws RestConnectionException, RaoException;
}
