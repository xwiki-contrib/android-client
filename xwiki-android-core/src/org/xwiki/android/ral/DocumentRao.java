package org.xwiki.android.ral;

import org.xwiki.android.ral.reference.DocumentReference;
import org.xwiki.android.ral.reference.EntityReference;
import org.xwiki.android.rest.RestConnectorException;
import org.xwiki.android.xmodel.entity.Document;

public interface DocumentRao extends Rao<Document>
{
    // conversion param
    int PAGE = 1;
    int OBJECTS = 2;
    int COMMENTS = 4;
    int TAGS = 8;
    int ATTACHMENTS = 16;
    int COMMENT_OBJECTS=32;
    int TAG_OBJECT=64;
    int ALL = 2147483647;

    /**
     * default semantics
     * 
     * @param dref
     * @return
     * @throws RestConnectorException
     * @throws RaoException
     */
    Document retreive(DocumentReference dref) throws RestConnectorException, RaoException;

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

    void delete(DocumentReference dRef) throws RestConnectorException, RaoException;
}
