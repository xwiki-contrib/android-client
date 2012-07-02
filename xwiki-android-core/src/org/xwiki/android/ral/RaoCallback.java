package org.xwiki.android.ral;

import java.io.IOException;
import java.util.List;

import org.xwiki.android.xmodel.entity.Document;

public interface RaoCallback<T> {
	
	void onProgressUpdate(int progress);
	/**
	 * 
	 * @param doc       The document constructed from server responses.
	 * 					Should configure the Rao to construct the Document entity before calling create().
	 * 					//TODO: currently not supported by underlying ReSTful Connector.
	 * @param success   whether the document was successfully created on the server.
	 * @param statusLine
	 */
	void onCreated(T res,  boolean success ,String statusLine);
	void ontFullyRetreived(T res);
	/**
	 * This method returns a EntityWrapper.(For automatic lazy fetching)
	 * @param res  The partially retrieved Resource.If the resouce is a document calling for an object that is not yet retreived inside the document will
	 * 				throw a runtime RESTfulLazyFetchException. The object will be automatically loaded and onDocumentFull/PartiallyRetreived()
	 * 				will be called accordingly.Please make sure to catch the exception if you call a Object or first class entity inside
	 * 				the document which haven't loaded yet.
	 * 
	 * @param args  List of newly loaded components of the document. Ex: Blog.BlogPostClass/* means all BlogPostClass objs
	 * 				have been loaded. 
	 * 
	 */
	void onPartiallyRetreived(T res, String... args);
	/**
	 * 
	 * @param rslts
	 * @param args  The list of components already loaded into the documents.
	 */
	void onQuerryComplete(List<T> rslts, String... args);
	/**
	 * 
	 * @param success
	 */
	void onDeleted(boolean success);
	/**
	 * Call back when IO error occures(ex: the Internet connectivity is not available). The client should handle this situation and choose whether to
	 * sync later,retry, or ignore.
	 * @param e  the exception passed back asynchronously to be handled by the client. 
	 */
	void handleException(IOException e);
	
}
