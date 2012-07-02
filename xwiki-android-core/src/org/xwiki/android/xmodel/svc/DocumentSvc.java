package org.xwiki.android.xmodel.svc;

import java.util.List;

import org.xwiki.android.ral.RestAPIUsageException;
import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.reference.DocumentReference;

public interface DocumentSvc {
	
	/**
	 * create the document in remote server.
	 * @return true if successful.
	 */
	void create(Document d, DocumentSvcCallbackInterface clbk);
	
	/**
	 * retreive the data of this doucment from the remote server.
	 * NOTE!:Please make sure to replace references for this document with the returned document. 
	 * @return this document filled with remote data. Maybe wrapped with a DocumentWrapper for lazy fetching.
	 * If in offline mode and
	 * 	if document was previously saved: will load the document and return saved doc
	 * 	else if some of the parts are cached in the Http Cache, fill the doucment with them. 
	 *  (The http cache normaly does a HEAD request and return cached copy only if the resource was not modified)
	 *  
	 *  @throws RestAPIUsageException (It is a {@link RuntimeException})
	 *  		If the document is Not in offline mode, and there is a problem with connectivity
	 *  		If problem in ReSTFul API usage format. ex: create request for already existing document.
	 */
	void retreive(DocumentReference dref, DocumentSvcCallbackInterface clbk);
	
	/**
	 * 
	 * @return
	 */
	void update(Document d, DocumentSvcCallbackInterface clbk);
	
	/**
	 * 
	 * @return
	 * @throws RestAPIUsageException
	 */
	void delete(DocumentReference dref, DocumentSvcCallbackInterface clbk);
	
	
	
	//public abstract EntityResponse<Document> createForDocument();
	//public abstract EntityResponse<Document> updateForDocument();
	//...
	//after spring is integrated.Currently extracting the Document from 
	// the response is not supported.
    
	void save(Document doc, DocumentSvcCallbackInterface clbk);
	
	void load(DocumentReference d, DocumentSvcCallbackInterface clbk);
	
//	/**
//	 *  search methods are for local file store searches.
//	 * @return
//	 */
//	void search(DocumentSvcCallbackInterface clbk);//TODO:not yet defined

}
