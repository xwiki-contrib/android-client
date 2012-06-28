package org.xwiki.android.xmodel.svc;

import java.util.List;

import org.xwiki.android.ral.RestFulException;
import org.xwiki.android.xmodel.entity.Document;

public interface DocumentSvc {
	
	/**
	 * create the document in remote server.
	 * @return true if successful.
	 */
	boolean create() throws RestFulException;
	
	/**
	 * retreive the data of this doucment from the remote server.
	 * NOTE!:Please make sure to replace references for this document with the returned document. 
	 * @return this document filled with remote data. Maybe wrapped with a DocumentWrapper for lazy fetching.
	 * If in offline mode and
	 * 	if document was previously saved: will load the document and return saved doc
	 * 	else if some of the parts are cached in the Http Cache, fill the doucment with them. 
	 *  (The http cache normaly does a HEAD request and return cached copy only if the resource was not modified)
	 *  
	 *  @throws RestFulException (It is a {@link RuntimeException})
	 *  		If the document is Not in offline mode, and there is a problem with connectivity
	 *  		If problem in ReSTFul API usage format. ex: create request for already existing document.
	 */
	Document retreive(Document ref);
	
	/**
	 * 
	 * @return
	 */
	boolean update() throws RestFulException;
	
	/**
	 * 
	 * @return
	 * @throws RestFulException
	 */
	boolean delete()throws RestFulException;
	
	
	
	//public abstract EntityResponse<Document> createForDocument();
	//public abstract EntityResponse<Document> updateForDocument();
	//...
	//after spring is integrated.Currently extracting the Document from 
	// the response is not supported.
    
	boolean save();
	Document load(Document d);
	
	/**
	 *  search methods are for local file store searches.
	 * @return
	 */
	List<Document> search();

}
