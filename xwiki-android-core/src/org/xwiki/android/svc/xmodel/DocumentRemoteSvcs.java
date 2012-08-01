package org.xwiki.android.svc.xmodel;

import org.xwiki.android.rest.reference.DocumentReference;
import org.xwiki.android.xmodel.entity.Document;

public interface DocumentRemoteSvcs
{

    /**
     * create the document in remote server.
     * 
     * @return true if successful.
     */
    void create(Document d, DocumentRemoteSvcCallbacks clbk);

    /**
     * Retrieve the data of this doucment from the remote server. NOTE!:Please make sure to replace references for this
     * document with the returned document.
     * 
     * @return this document filled with remote data. Maybe wrapped with a DocumentWrapper for lazy fetching. If in
     *         offline mode and if document was previously saved: will load the document and return saved doc else if
     *         some of the parts are cached in the Http Cache, fill the document with them. (The http cache normally
     *         does a HEAD request and return cached copy only if the resource was not modified)
     * @throws IllegalRestUsageException (It is a {@link RuntimeException}) If the document is Not in offline mode, and
     *             there is a problem with connectivity If problem in ReSTFul API usage format. ex: create request for
     *             already existing document.
     */
    void retreive(DocumentReference dref, DocumentRemoteSvcCallbacks clbk);

    /**
     * @return
     */
    void update(Document d, DocumentRemoteSvcCallbacks clbk);

    /**
     * @return
     * @throws IllegalRestUsageException
     */
    void delete(DocumentReference dref, DocumentRemoteSvcCallbacks clbk);
}
